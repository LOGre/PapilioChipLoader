/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package papilioChip.sap;

import java.io.IOException;
import papilioChip.Loader;
import net.sourceforge.lhadecompressor.LhaException;
import papilioChip.FramesBuffer;
import papilioChip.Header;
import papilioChip.ProcessException;

/**
 *
 * @author admin
 */
public class SAPLoader extends Loader
{
    final protected static int LHA_BUFFSER_SIZE = 4096;
    private SAPHeader header;
    private byte[][] framesData;
    private boolean alreadyDecoded = false;

    public SAPLoader()
    {
        super();
    }

    public void depack(String filename) throws ProcessException
    {


        // reset the stuff if called 2 times
        alreadyDecoded = false;
        buffer = null;
        header = null;
        framesData = null;
        
        try
        {
            depackLHA(filename);
        }
        catch (ProcessException ex)
        {
            System.out.println("Seems not to be a LHA packed file, trying raw");
            loadFile(filename);
        }
    }

    public Header decodeFileFormat() throws ProcessException
    {
        if (buffer == null)
        {
            throw new SAPProcessException("SAP not depacked yet");
        }
        if (alreadyDecoded)
        {
            return this.header;
        }

        try
        {
            header = new SAPHeader();
            int framesStart = 0;

            // Read ID
            header.checkStartString(getStringEOL());
            boolean hasMetaData = true;
            while (hasMetaData)
            {
                try
                {
                    header.parseMetadata(getStringEOL());
                }
                catch (SAPNoMoreMetadataException ex)
                {
                    hasMetaData = false;
                    framesStart = ex.offset;
                }
            }

            checkType(header.getType());

            // decode registers
            int nbFrames = header.getFrames();
            if(nbFrames == 0 ) nbFrames = (buffer.capacity() - framesStart) / 9;
            header.setFrames(nbFrames);
            
            framesData = new byte[nbFrames][9];
            for (int frames = 0; frames < nbFrames; frames++)
            {
                for (int reg = 0; reg < 9; reg++)
                {
                    framesData[frames][reg] = buffer.get();
                }
            }

            alreadyDecoded = true;
        }
        catch (SAPNoMoreMetadataException ex)
        {
            throw new SAPProcessException("Error while parsing the file : " + ex.getMessage(), ex);
        }
        catch (SAPProcessException ex)
        {
            throw new SAPProcessException("Error while parsing the file : " + ex.getMessage(), ex);
        }


        return header;

    }

    public void dumpFrames()
    {
        if(alreadyDecoded)
        {
        int nbFrames = header.getFrames();

        System.out.println("Registers    00 01 02 03 04 05 06 07 08");
        System.out.println("---------------------------------------");
        for(int i=0;i<nbFrames;i++)
        {
            System.out.print("Frame " + (Integer.toHexString(0x10000 | i )).substring(1).toUpperCase() + " : ");
            for(int j=0;j<9;j++)
            {
                System.out.print(Integer.toHexString(0x100 | (framesData[i][j] & 0xFF )).substring(1).toUpperCase() + " ");
            }
            System.out.println("");
        }
        }
    }

    /**
     * Retrieve the frames
     * @return a FramesBuffer
     */
    public FramesBuffer getFramesBuffer()
    {
        return new FramesBuffer(framesData, header.getFrames(), 9);
    }
    
    /**
     * Util to read a EOL terminated string from the bytebuffer
     * @param n
     * @return the EOL String 0D 0A
     */
    private String getStringEOL() throws SAPProcessException, SAPNoMoreMetadataException
    {
        String res = "";
        byte aByte = buffer.get();

        if (aByte == (byte)0xFF)
        {
            if (buffer.get() == (byte)0xFF)
            {
                throw new SAPNoMoreMetadataException("binary header detected", buffer.position());
            }
        }

        while (aByte != (byte)0x0D)
        {
            res += new Character((char) (aByte)).toString();
            aByte = buffer.get();
        }
        if (aByte == (byte)0x0D)
        {
            aByte = buffer.get();
            if (aByte != (byte)0x0A)
            {
                throw new SAPProcessException("invalid header");
            }
        }

        return res;
    }

    private void checkType(char type) throws SAPProcessException
    {
        if (type != SAPHeader.TYPE_R)
        {
            throw new SAPProcessException("Type " + type + " not managed");
        }

    }


}
