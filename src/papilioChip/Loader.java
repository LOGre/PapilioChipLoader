/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package papilioChip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.lhadecompressor.LhaEntry;
import net.sourceforge.lhadecompressor.LhaException;
import net.sourceforge.lhadecompressor.LhaFile;
import papilioChip.sap.SAPLoader;
import papilioChip.ym.YMLoader;
import papilioChip.ym.YMProcessException;

/**
 *
 * @author admin
 */
public abstract class Loader
{

    protected ByteBuffer buffer;
    final protected static int LHA_BUFFSER_SIZE = 4096;

    public abstract void depack(String filename) throws ProcessException;

    public abstract Header decodeFileFormat() throws ProcessException;

    public abstract void dumpFrames();

    public abstract FramesBuffer getFramesBuffer();

    public static Loader getLoader(String extension) throws ProcessException
    {
        if (extension.equalsIgnoreCase(Header.SAPFILEEXT))
        {
            return new SAPLoader();
        }
        else if (extension.equalsIgnoreCase(Header.YMFILEEXT))
        {
            return new YMLoader();
        }
        else
        {
            throw new ProcessException("Format not managed : " + extension);
        }
    }

    /**
     * Depack the file (LHA compression)
     * @param filename
     * @throws ProcessException, LhaException
     */
    protected void depackLHA(String filename) throws ProcessException
    {
        try
        {
            LhaFile lhafile = new LhaFile(filename);
            LhaEntry entry = lhafile.getEntry(0);
            System.out.println("    EXTRACT FILE    = " + entry.getFile());
            System.out.println("    METHOD          = " + entry.getMethod());
            System.out.println("    COMPRESSED SIZE = " + entry.getCompressedSize());
            System.out.println("    ORIGINAL SIZE   = " + entry.getOriginalSize());
            System.out.println("    TIME STAMP      = " + entry.getTimeStamp());
            System.out.println("    OS ID           = " + (char) entry.getOS());
            InputStream in = new BufferedInputStream(lhafile.getInputStream(entry), LHA_BUFFSER_SIZE);
            ByteArrayOutputStream bastream = new ByteArrayOutputStream((int) entry.getOriginalSize());
            bufferStream(in, bastream, ByteOrder.BIG_ENDIAN, LHA_BUFFSER_SIZE);
            bastream.close();
            in.close();
            lhafile.close();
        }
        catch (LhaException ex)
        {
            throw new ProcessException(ex.getMessage(), ex);
        }
        catch (IOException ex)
        {
            throw new ProcessException(ex.getMessage(), ex);
        }
    }

    protected void loadFile(String filename) throws ProcessException
    {
        try
        {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            InputStream in = new BufferedInputStream(fis);
            ByteArrayOutputStream bastream = new ByteArrayOutputStream((int) file.length());

            bufferStream(in, bastream, ByteOrder.BIG_ENDIAN, LHA_BUFFSER_SIZE);

            bastream.close();
            in.close();
            fis.close();
        }
        catch (IOException ex)
        {
            throw new YMProcessException(ex);
        }
    }

    protected void bufferStream(InputStream in, ByteArrayOutputStream bastream, ByteOrder order, int bufferSize) throws ProcessException
    {
        try
        {
            byte[] buff = new byte[bufferSize];
            int len = 0;
            while (true)
            {
                len = in.read(buff, 0, bufferSize);
                if (len < 0)
                {
                    break;
                }
                if (len < bufferSize)
                {
                    bastream.write(buff, 0, len);
                }
                else
                {
                    bastream.write(buff);
                }
            }
            bastream.flush();
            buffer = ByteBuffer.wrap(bastream.toByteArray());

            // WARNING: All DWORD or WORD are stored in MOTOROLA order in the file (INTEL reverse)
            buffer.order(order);
        }
        catch (IOException ex)
        {
            throw new ProcessException(ex.getMessage(), ex);
        }
    }
}
