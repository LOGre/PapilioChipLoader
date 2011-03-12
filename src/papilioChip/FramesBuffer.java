/*
 * FramesBuffer class
 */

package papilioChip;

import java.util.Vector;

/**
 *
 * @author shazz
 */
public class FramesBuffer
{
    private Vector framesData;
    private int framesNb;
    private int registersNb;

    /**
     * Constructor
     * @param data
     * @param framesNb
     * @param registersNb
     */
    public FramesBuffer(byte[][] data, int framesNb, int registersNb)
    {
        this.framesData = new Vector();

        // Store reg number then reg value

        for(int frames=0; frames<framesNb; frames++)
        {
            byte[] regs = new byte[registersNb*2];
            for(int reg=0; reg<registersNb; reg++)
            {
                regs[reg*2] = (byte) (reg & 0xFF);
                regs[(reg*2)+1] = data[frames][reg];
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
    public Vector getFramesData() {
        return framesData;
    }

    /**
     * Get the number of frames available
     * @return the number of frames available
     */
    public int getFramesNb() {
        return framesNb;
    }

    /**
     * Get the number of registers per frame
     * @return the number of registers per frame
     */
    public int getRegistersNb() {
        return registersNb;
    }

    
}
