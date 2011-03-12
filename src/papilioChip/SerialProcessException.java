/*
 * SerialProcessEsception
 */

package papilioChip;

/**
 *
 * @author shazz
 */
class SerialProcessException extends Exception
{

    public SerialProcessException(Throwable thrwbl) 
    {
        super( thrwbl );
    }

    public SerialProcessException(String string) 
    {
        super( string );
    }

    public SerialProcessException(String string, Throwable thrwbl) 
    {
        super( string, thrwbl );
    }

    

}
