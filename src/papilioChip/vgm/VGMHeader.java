/*
 * VGM Header class
 */
package papilioChip.vgm;

import papilioChip.Header;

/**
 *
 * @author shazz
 */
public class VGMHeader extends Header
{

    /**
     *
     */
    public final static String STARTSTRING = "Vgm ";
    private String id;
    private int offset;
    private float version;
    private int sn76489Clock;
    private int sn76489feedback;
    private int sn76489shiftregWidth;
    private int sn76489flags;
    private int ym2413Clock;
    private int ym2612Clock;
    private int ym2151Clock;
    private int gd3offset;
    private int samplesNumber;
    private int loopOffset;
    private int loopSamplesNumber;
    private int rate;
    private int dataOffset;
    private int frames;



    /**
     *
     * @param stringEOL
     * @throws VGMProcessException
     */
    public void checkStartString(String stringEOL) throws VGMProcessException
    {
        if (!stringEOL.equals(STARTSTRING))
        {
            throw new VGMProcessException("Not a valid VGM file (Wrong start string)");
        }
    }

    /**
     *
     */
    public void dump()
    {
        System.out.println("---------------------------------------");
        System.out.println("Offset : " + this.offset);
        System.out.println("Version : " + this.version);
        System.out.println("SN76489 Clk : " + this.sn76489Clock);
        System.out.println("YM2143 Clk : " + this.ym2413Clock);
        System.out.println("YM2151 Clk : " + this.ym2151Clock);
        System.out.println("YM2612 Clk : " + this.ym2612Clock);
        System.out.println("GD3 Offet : " + this.gd3offset);
        System.out.println("Total samples : " + this.samplesNumber);
        System.out.println("Loop offset : " + this.loopOffset);
        System.out.println("Loop samples : " + this.loopSamplesNumber);
        System.out.println("Rate : " + this.rate);
        System.out.println("SN76489 feedback : " + this.sn76489feedback);
        System.out.println("SN76489 flags : " + this.sn76489flags);
        System.out.println("SN76489 shift reg : " + this.sn76489shiftregWidth);
        System.out.println("VGM offset : " + this.dataOffset);
        System.out.println("---------------------------------------");
    }

    public void setSn76489params(int params)
    {
        setSn76489feedback(params & 0x0FFFF);
        setSn76489shiftregWidth( (params >> 16) & 0x0FF);
        setSn76489flags((params >> 24) & 0x0FF);
    }

    public int getDataOffset()
    {
        return dataOffset;
    }

    public void setDataOffset(int dataOffset)
    {
        this.dataOffset = dataOffset;
    }

    public int getGd3offset()
    {
        return gd3offset;
    }

    public void setGd3offset(int gd3offset)
    {
        this.gd3offset = gd3offset;
    }

    public int getLoopOffset()
    {
        return loopOffset;
    }

    public void setLoopOffset(int loopOffset)
    {
        this.loopOffset = loopOffset;
    }

    public int getLoopSamplesNumber()
    {
        return loopSamplesNumber;
    }

    public void setLoopSamplesNumber(int loopSamplesNumber)
    {
        this.loopSamplesNumber = loopSamplesNumber;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public int getSamplesNumber()
    {
        return samplesNumber;
    }

    public void setSamplesNumber(int samplesNumber)
    {
        this.samplesNumber = samplesNumber;
    }

    public int getSn76489Clock()
    {
        return sn76489Clock;
    }

    public void setSn76489Clock(int sn76489Clock)
    {
        this.sn76489Clock = sn76489Clock;
    }

    public int getSn76489feedback()
    {
        return sn76489feedback;
    }

    public void setSn76489feedback(int sn76489feedback)
    {
        this.sn76489feedback = sn76489feedback;
    }

    public int getSn76489flags()
    {
        return sn76489flags;
    }

    public void setSn76489flags(int sn76489flags)
    {
        this.sn76489flags = sn76489flags;
    }

    public int getSn76489shiftregWidth()
    {
        return sn76489shiftregWidth;
    }

    public void setSn76489shiftregWidth(int sn76489shiftregWidth)
    {
        this.sn76489shiftregWidth = sn76489shiftregWidth;
    }

    public float getVersion()
    {
        return version;
    }

    public void setVersion(float version)
    {
        this.version = version;
    }

    public int getYm2413Clock()
    {
        return ym2413Clock;
    }

    public void setYm2413Clock(int ym2413Clock)
    {
        this.ym2413Clock = ym2413Clock;
    }

    public int getYm2612Clock()
    {
        return ym2612Clock;
    }

    public void setYm2612Clock(int ym2612Clock)
    {
        this.ym2612Clock = ym2612Clock;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getYm2151Clock()
    {
        return ym2151Clock;
    }

    public void setYm2151Clock(int ym2151Clock)
    {
        this.ym2151Clock = ym2151Clock;
    }

    

    @Override
    public int getReplayRate()
    {
        return rate;
    }

    @Override
    public int getLoopFrames()
    {
        return loopOffset;
    }

    void setFrames(int nbFrames)
    {
        this.frames = nbFrames;
    }

    public int getFrames()
    {
        return frames;
    }


    

}
