/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip;

import java.util.Vector;

/**
 *
 * @author admin
 */
interface FramesBuffer {

    public Vector getFramesData();

    /**
     * Get the number of frames available
     * @return the number of frames available
     */
    public int getFramesNb();

    /**
     * Get the number of registers per frame
     * @return the number of registers per frame
     */
    public int getRegistersNb();

    public int getFrameSize();

    public void dumpToFile(int nbFrames);
}
