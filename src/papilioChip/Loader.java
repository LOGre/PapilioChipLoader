/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip;

/**
 *
 * @author admin
 */
public abstract class Loader
{
    public abstract void depack(String filename) throws ProcessException;

    public abstract Header decodeFileFormat() throws ProcessException;

    public abstract void dumpFrames();

    public abstract FramesBuffer getFramesBuffer();
}
