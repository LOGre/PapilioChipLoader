/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip;

/**
 *
 * @author admin
 */
public class ProcessException extends Exception
{
    public ProcessException(Throwable thrwbl)
    {
        super( thrwbl );
    }

    public ProcessException(String string)
    {
        super( string );
    }

    public ProcessException(String string, Throwable thrwbl)
    {
        super( string, thrwbl );
    }
}
