/*
 * Header abstract class
 */

package papilioChip;

/**
 *
 * @author shazz
 */
public abstract class Header
{
    public final static String YMFILEEXT = "ym";
    public final static String SAPFILEEXT = "sap";
    public final static String VGMFILEEXT = "vgm";

    public abstract void dump();

    public abstract int getReplayRate();

    public abstract int getLoopFrames();

}
