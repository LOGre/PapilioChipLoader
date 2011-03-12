/*
 * YMProcessException Class
 */

package papilioChip.ym;

import papilioChip.ProcessException;

/**
 *
 * @author shazz
 */
public class YMProcessException extends ProcessException
{

    public YMProcessException(Throwable thrwbl) 
    {
        super( thrwbl );
    }

    public YMProcessException(String string) 
    {
        super (string);
    }

    public YMProcessException(String string, Throwable thrwbl)
    {
        super( string, thrwbl );
    }



    
}
