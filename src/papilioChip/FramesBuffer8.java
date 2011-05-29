/*
 * FramesBuffer8 class
 */
package papilioChip;

import java.io.FileOutputStream;
import java.util.Vector;

/**
 *
 * @author shazz
 */
public class FramesBuffer8 implements FramesBuffer
{

    private Vector framesData;
    private int framesNb;
    private int registersNb;
    private byte[][] data;

    /**
     * Constructor
     * @param data
     * @param framesNb
     * @param registersNb
     */
    public FramesBuffer8(byte[][] data, int framesNb, int registersNb)
    {
        this.framesData = new Vector();
        this.data = data;
        // Store reg number then reg value

        for (int frames = 0; frames < framesNb; frames++)
        {
            byte[] regs = new byte[registersNb * 2];
            for (int reg = 0; reg < registersNb; reg++)
            {
                regs[reg * 2] = (byte) (reg & 0xFF);
                regs[(reg * 2) + 1] = data[frames][reg];
            }
            this.framesData.add(regs);
        }
        this.framesNb = framesNb;
        this.registersNb = registersNb;
    }

    /**
     * Get the list of frames data
     * @return the list of frames data
     */
    public Vector getFramesData()
    {
        return framesData;
    }

    public void dumpToFile(int nbFrames)
    {
        if (data == null)
        {
            System.err.println("no data to dump");
        }

        //copy data
        byte[][] data14 = new byte[nbFrames][28];
        for (int frames = 0; frames < nbFrames; frames++)
        {
            for (int reg = 0; reg < 14; reg++)
            {
                data14[frames][(reg * 2)] = (byte) reg;
                data14[frames][(reg * 2) + 1] = data[frames][reg];
            }

        }

        String strFilePath = "D://temp//dump3.out";
        try
        {
            FileOutputStream fos = new FileOutputStream(strFilePath);

            /*
             * To write byte array to a file, use
             * void write(byte[] bArray) method of Java FileOutputStream class.
             *
             * This method writes given byte array to a file.
             */
            for (int frames = 0; frames < nbFrames; frames++)
            {
                fos.write(data14[frames]);
            }

            /*
             * Close FileOutputStream using,
             * void close() method of Java FileOutputStream class.
             *
             */

            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Get the number of frames available
     * @return the number of frames available
     */
    public int getFramesNb()
    {
        return framesNb;
    }

    /**
     * Get the number of registers per frame
     * @return the number of registers per frame
     */
    public int getRegistersNb()
    {
        return registersNb;
    }

    public int getFrameSize()
    {
        return 8;
    }


}
