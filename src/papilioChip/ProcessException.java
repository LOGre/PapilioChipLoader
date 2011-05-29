/*
 * Process Exception class
 */

package papilioChip;

/**
 *
 * @author shazz
 */
public class ProcessException extends Exception
{
    /**
     * 
     * @param thrwbl
     */
    public ProcessException(Throwable thrwbl)
    {
        super( thrwbl );
    }

    /**
     *
     * @param string
     */
    public ProcessException(String string)
    {
        super( string );
    }

    /**
     *
     * @param string
     * @param thrwbl
     */
    public ProcessException(String string, Throwable thrwbl)
    {
        super( string, thrwbl );
    }
}
