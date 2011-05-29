/*
 * Special Exception class for SAP header parsing
 */

package papilioChip.sap;

/**
 *
 * @author shazz
 */
public class SAPNoMoreMetadataException extends Exception
{
    public int offset = 0;

    public SAPNoMoreMetadataException(Throwable thrwbl, int offset)
    {
        super( thrwbl );
        this.offset = offset;
    }

    public SAPNoMoreMetadataException(String string, int offset)
    {
        super( string );
        this.offset = offset;
    }

    public SAPNoMoreMetadataException(int offset)
    {
         super( );
         this.offset = offset;
    }

    public SAPNoMoreMetadataException(String string, Throwable thrwbl, int offset)
    {
        super( string, thrwbl );
        this.offset = offset;
    }
}
