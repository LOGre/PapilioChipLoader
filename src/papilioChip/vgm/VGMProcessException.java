/*
 * VGM Processing exception class
 */

package papilioChip.vgm;

import papilioChip.ProcessException;

/**
 *
 * @author shazz
 */
public class VGMProcessException extends ProcessException
{
    /**
     *
     * @param thrwbl
     */
    public VGMProcessException(Throwable thrwbl)
    {
        super( thrwbl );
    }

    /**
     *
     * @param string
     */
    public VGMProcessException(String string)
    {
        super( string );
    }

    /**
     *
     * @param string
     * @param thrwbl
     */
    public VGMProcessException(String string, Throwable thrwbl)
    {
        super( string, thrwbl );
    }
}
