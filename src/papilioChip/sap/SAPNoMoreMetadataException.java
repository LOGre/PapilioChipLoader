/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip.sap;

/**
 *
 * @author admin
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
