/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package papilioChip.vgm;

import java.util.Vector;

/**
 *
 * @author admin
 */
public class VGMFramePacket
{
    private Vector packets;

    public VGMFramePacket()
    {
        this.packets = new Vector();
    }

    public void addPacket(VGMRegister reg)
    {
        packets.add(reg);
    }

    public int getPacketNb()
    {
        return packets.size();
    }

    public VGMRegister getPacketVal(int nb)
    {
        return (VGMRegister) packets.get(nb);
    }

    @Override
    public String toString()
    {
        return "Contains " + packets.size() + " VGM Registers";
    }



}
