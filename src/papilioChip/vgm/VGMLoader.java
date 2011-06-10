/*
 * The YMLoader class
 * Based on VGM format
 */
package papilioChip.vgm;

import java.util.Vector;
import papilioChip.FramesBuffer16;
import papilioChip.Header;
import papilioChip.Loader;
import papilioChip.ProcessException;

/**
 * VGM Loader
 * @author shazz
 */
public class VGMLoader extends Loader
{
    private VGMHeader header;

    private int[][] framesData;

    private boolean alreadyDecoded = false;

    private final int VGM_CMD_SN76486 = 0x50;
    private final int VGM_CMD_YM2413 = 0x51;
    private final int VGM_CMD_YM2612_P0 = 0x52;
    private final int VGM_CMD_YM2612_P1 = 0x53;
    private final int VGM_CMD_ENDFRAME = 0x62;
    private final int VGM_CMD_ENDATA = 0x66;
    private final int VGM_CMD_BLANKFRAMES = 0x61;

    /**
     * Get the Header
     * @return the header
     */
    public VGMHeader getHeader() {
        return header;
    }

    public void depack(String filename) throws ProcessException
    {
        buffer = null;
        header = null;
        alreadyDecoded = false;
        framesData = null;

        try
        {
            loadFile(filename);
            //depackGZ(filename);
        }
        catch (ProcessException ex)
        {
            System.out.println("Seems not to be a GZ packed file, trying raw");
            loadFile(filename);
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println("Seems not to be a GZ packed file, trying raw");
            loadFile(filename);
        }
    }

    /**
     * Decode the YM file format
     * @return the YM Header
     * @throws YMProcessException
     */
    public Header decodeFileFormat() throws ProcessException
    {
        if(buffer == null) throw new VGMProcessException("VGM not depacked yet");
        if(alreadyDecoded) return this.header;

        try
        {
            header = new VGMHeader();

            // Read ID
            header.setId(getString(4));
            checkHeader(header.getId());

            // Read stuff
            header.setOffset(getMotoInt());

            int ver = buffer.getInt();
            String minVer = Integer.toHexString(ver >> 24);
            String majVer = Integer.toHexString((ver >> 16) & 0xFF);
            header.setVersion(Float.parseFloat( majVer + "." + minVer ));
            header.setSn76489Clock(getMotoInt());
            header.setYm2413Clock(getMotoInt());
            header.setGd3offset(getMotoInt());
            header.setSamplesNumber(getMotoInt());
            header.setLoopOffset(getMotoInt());
            header.setLoopSamplesNumber(getMotoInt());
            header.setRate(getMotoInt());

            header.setDataOffset(0x40);
            if(header.getVersion() >= 1.10f)
            {
                header.setSn76489params(getMotoInt());
                header.setYm2612Clock(getMotoInt());
                header.setYm2151Clock(getMotoInt());
            }
            if(header.getVersion() >= 1.50f)
            {
                header.setDataOffset(getMotoInt());
            }


            // search end
            
            // move to data
            buffer.position(header.getDataOffset());
            // get framepackets
            Vector paquetVect = new Vector();
            boolean dataEnded = false;
            int nbFrames = 0;

            while(!dataEnded)
            {
                boolean frameEnded = false;
                VGMFramePacket packets = new VGMFramePacket();
                int nbPackets = 0;
                int framesToSkipped = 1;

                while(!frameEnded & !dataEnded)
                {
                    int command = buffer.get();
                    switch ( command )
                    {
                        case VGM_CMD_SN76486:
                            packets.addPacket(new VGMRegister(buffer.get()));
                            nbPackets++;
                            break;
                        case VGM_CMD_YM2413:
                        case VGM_CMD_YM2612_P0:
                        case VGM_CMD_YM2612_P1:
                            buffer.position(buffer.position() + 2);
                            break;
                        case VGM_CMD_ENDFRAME:
                            framesToSkipped = 1;
                            frameEnded = true;
                            break;
                        case VGM_CMD_ENDATA:
                            framesToSkipped = 1;
                            dataEnded = true;
                            break;
                        case VGM_CMD_BLANKFRAMES:
                            byte nbF1 = buffer.get();
                            byte nbF2 = buffer.get();
                            framesToSkipped = (((nbF2 & 0xFF) << 8) | (nbF1 & 0xFF))/735 ;
                            //buffer.position(buffer.position() + 2);
                            System.out.println("Skip : " + framesToSkipped + " frames");
                            frameEnded = true;
                            break;
                        default:
                            System.out.println("Command not managed : " + Integer.toHexString(command) + " at pos 0x0" + Integer.toHexString(buffer.position() - 3));
                            break;
                     }
                }
                // copy data
                if(framesToSkipped > 1)
                {
                for(int l=0; l<framesToSkipped; l++)
                    paquetVect.add(new VGMFramePacket());
                }
                else
                {
                    paquetVect.add(packets);
                }
                nbFrames = nbFrames + framesToSkipped;

                System.out.println("Frame " + nbFrames + " parsed with " + nbPackets + " packets");
            }

            header.setFrames(nbFrames);
            framesData = new int[nbFrames][8];

            //set to FFFF all stuff
            for(int frames=0;frames<nbFrames;frames++)
            {
                for(int reg=0;reg<8;reg++)
                {
                    framesData[frames][reg] = 0xFFFF;
                }
            }

            for(int frames=0;frames<nbFrames;frames++)
            {
                VGMFramePacket packet = (VGMFramePacket) paquetVect.get(frames);
                VGMRegister reg, lastReg = null;
                for(int i=0; i<packet.getPacketNb(); i++)
                {
                    reg = packet.getPacketVal(i);
                    if(reg.getNbByte() == 1)
                    {
                        if(reg.getType() == VGMRegister.VGMREG_TYPE_FREQ)
                        {
                            framesData[frames][reg.getAddr()] = reg.getData();
                            lastReg = reg;
                        }
                        else
                        {
                            framesData[frames][reg.getAddr()] = reg.getData();
                            lastReg = null;
                        }
                    }
                    else if(reg.getNbByte() == 0)
                    {
                        if(lastReg == null) throw new ProcessException("Missing Freq reg !");
                        else
                        {
                            int addr = lastReg.getAddr();
                            framesData[frames][addr] |= (reg.getData() << 4);
                        }
                    }                  
                }

            }


            alreadyDecoded = true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new VGMProcessException("Error while parsing the file : " + ex.getMessage(), ex);
        }


        return header;
    }



    /**
     * dump the YM header to screen
     */
    public void dumpHeader()
    {
        header.dump();
    }

    /**
     * dump the VGM frames to screen
     */
    public void dumpFrames()
    {
        int nbFrames = header.getFrames();

        System.out.println("\n--------------------------------------------------");
        System.out.println("Registers   FRQ1 ATN1 FRQ2 ATN2 FRQ3 ATN3 NCTL NATN");
        System.out.println("---------------------------------------------------");
        for(int i=0;i<nbFrames;i++)
        {
            System.out.print("Frame " + (Integer.toHexString(0x1000 | i )).substring(1).toUpperCase() + " : ");
            for(int j=0;j<8;j++)
            {
                System.out.print(Integer.toHexString(0x10000 | (framesData[i][j] & 0xFFFF )).substring(1).toUpperCase() + " ");
            }
            System.out.println("");
        }
        //System.out.println("bah... no dump, it works :)\n");
    }

    /**
     * Retrieve the frames
     * @return a FramesBuffer8
     */
    public FramesBuffer16 getFramesBuffer()
    {
        return new FramesBuffer16(framesData, header.getFrames(), 8);
    }

    /**
     * Util to read n char and build a java string from a Bytebuffer
     * @param n
     * @return the string
     */
    private String getString(int n)
    {
        String res = "";
        for(int i=0;i<n;i++)
        {
            res += (new Character((char) (buffer.get()))).toString();
        }

        return res;
    }

    /**
     * Util to read n bytes
     * @param n
     * @return the bytes
     */
    private byte[] getByte(int n)
    {
        byte[] res = new byte[n];
        for(int i=0;i<n;i++)
        {
            res[i] = buffer.get();
        }

        return res;
    }

    /**
     * Util to read n integer
     * @param n
     * @return the int
     */
    private int getInt(int n)
    {
        int res = 0;
        for(int i=0;i<n;i++)
        {
            res = (res << 8) + (int) (buffer.get() & 0xFF);
        }

        return  res;
    }

    /**
     * Util to read Motorola Long Word
     * @return the int in reverse byte order
     */
    private int getMotoInt()
    {
            byte[] aByte = getByte(4);
            return (((aByte[3] & 0xFF) << 24) | ((aByte[2] & 0xFF) << 16) | ((aByte[1] & 0xFF) << 8) | ((aByte[0] & 0xFF)));
    }

    /**
     * Check header validity
     * @throws YMProcessException
     */
    private void checkHeader(String id) throws VGMProcessException
    {
        if(id.compareTo(VGMHeader.STARTSTRING) != 0)
            throw new VGMProcessException("ID tag not recognized");
    }

}
