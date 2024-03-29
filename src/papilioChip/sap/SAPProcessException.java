/*
 * SAP Processing exception class
 */

package papilioChip.sap;

import papilioChip.ProcessException;

/**
 *
 * @author shazz
 */
public class SAPProcessException extends ProcessException
{
    /**
     *
     * @param thrwbl
     */
    public SAPProcessException(Throwable thrwbl)
    {
        super( thrwbl );
    }

    /**
     *
     * @param string
     */
    public SAPProcessException(String string)
    {
        super( string );
    }

    /**
     *
     * @param string
     * @param thrwbl
     */
    public SAPProcessException(String string, Throwable thrwbl)
    {
        super( string, thrwbl );
    }
}
