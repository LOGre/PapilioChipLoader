/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip.sap;

import papilioChip.ProcessException;

/**
 *
 * @author admin
 */
public class SAPProcessException extends ProcessException
{
    public SAPProcessException(Throwable thrwbl)
    {
        super( thrwbl );
    }

    public SAPProcessException(String string)
    {
        super( string );
    }

    public SAPProcessException(String string, Throwable thrwbl)
    {
        super( string, thrwbl );
    }
}
