/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip.vgm;

/**
 *
 * @author admin
 */
public class VGMRegister
{
    public static final int VGMREG_TYPE_FREQ = 0;
    public static final int VGMREG_TYPE_ATTN = 1;
    public static final int VGMREG_TYPE_NOISECTRL = 2;

    private int nbByte;
    private int addr;
    private int data;
    private int type;


    public VGMRegister(byte val)
    {
        nbByte = (val & 0xFF) >> 7;

        if(nbByte == 1)
        {
            addr = (val & 0x70) >> 4;
            data = (val & 0xF);
        }
        else
        {
            data = (val & 0x7F);
        }

        switch(addr)
        {
            case 0:
            case 2:
            case 4:
                type = VGMREG_TYPE_FREQ;
                break;
            case 1:
            case 3:
            case 5:
            case 7:
                type = VGMREG_TYPE_ATTN;
                break;
            case 6:
                type = VGMREG_TYPE_NOISECTRL;
                break;
        }
    }

    public int getAddr()
    {
        return addr;
    }

    public int getData()
    {
        return data;
    }

    public int getType()
    {
        return type;
    }

    public int getNbByte()
    {
        return nbByte;
    }

    @Override
    public String toString()
    {
        return "type: " + type + " - ";
    }

 

}
