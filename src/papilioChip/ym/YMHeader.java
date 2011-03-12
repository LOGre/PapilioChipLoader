/*
 * YM Header class
 */

package papilioChip.ym;

import papilioChip.Header;

/**
 *
 * @author admin
 */
public class YMHeader extends Header
{
    private String id;
    private String leo = "no sig";
    private int frames = 0;
    private boolean interleaved = false;
    private boolean digiDrumsSignedSamples = false;
    private boolean digiDrums4bitsSTFormat = false;
    private short digidrums = 0;
    private int clock = 2000000;
    private short frequency = 50;
    private int loopFrames = 0;
    private int futurDataSize = 0;

    private String songName = "No title";
    private String authorName = "No author";
    private String songComment = "No comments";

    public static final String YM2 = "YM2!";
    public static final String YM3 = "YM3!";
    public static final String YM3b = "YM3b";
    public static final String YM4 = "YM4!";
    public static final String YM5 = "YM5!";
    public static final String YM6 = "YM6!";
    public static final String MIX1 = "MIX1";
    public static final String YMT2 = "YMT2";
    public static final String[] idtags = { YM2, YM3, YM3b, YM4, YM5, YM6, MIX1, YMT2};
    public static final String SIGNATURE = "LeOnArD!";

    /**
     * Constructor
     */
    public YMHeader() {

    }

    /**
     * dump the YM header
     */
    public void dump()
    {
        System.out.println("---------------------------------------------------");
        System.out.println("YM version : " + getId());
        System.out.println("Code by : " + getLeo());

        int minutes = Math.abs(getFrames()/getReplayRate()/60);
        int sec = Math.abs(getFrames()/getReplayRate() - (minutes*60));
        System.out.println("Frames dumped : " + getFrames() + " (" + minutes + " min " + sec + " sec)");
        if(isInterleaved()) System.out.println("Interleaved : Yes");
        else System.out.println("Interleaved : No");

        if(getDigidrums() > 0)
        {
            System.out.print(getDigidrums() + " digidrums which are ");
            if(!isDigiDrumsSignedSamples()) System.out.print("not ");
            System.out.print("signed and ");
            if(!isDigiDrums4bitsSTFormat()) System.out.print("not ");
            System.out.println("in ST format");
        }
        else
        {
            System.out.println("No digidrums");
        }

        System.out.println("YM clock : " + getClock() + " Hz");
        System.out.println("Replay frequency : " + getReplayRate() + " Hz");


        if(getLoopFrames() > 0)
        {
            minutes = Math.abs(getLoopFrames()/getReplayRate()/60);
            sec = Math.abs(getLoopFrames()/getReplayRate() - (minutes*60));
            System.out.println("Loop after " + getLoopFrames() + " frames (" + minutes + " min " + sec + " sec)");
        }
        if(getFuturDataSize() != 0)
            System.out.println("Unused : " + getFuturDataSize());

        System.out.println("Song : " + getSongName());
        System.out.println("Author : " + getAuthorName());
        System.out.println("Comments : " + getSongComment());
        System.out.println("---------------------------------------------------");
    }

    /**
     *
     * @return
     */
    public boolean isDigiDrums4bitsSTFormat() {
        return digiDrums4bitsSTFormat;
    }

    /**
     *
     * @param areDigiDrums4bitsSTFormat
     */
    public void setDigiDrums4bitsSTFormat(boolean areDigiDrums4bitsSTFormat) {
        this.digiDrums4bitsSTFormat = areDigiDrums4bitsSTFormat;
    }

    /**
     *
     * @return
     */
    public boolean isDigiDrumsSignedSamples() {
        return digiDrumsSignedSamples;
    }

    /**
     *
     * @param areDigiDrumsSignedSamples
     */
    public void setDigiDrumsSignedSamples(boolean areDigiDrumsSignedSamples) {
        this.digiDrumsSignedSamples = areDigiDrumsSignedSamples;
    }

    /**
     *
     * @return
     */
    public boolean isInterleaved() {
        return interleaved;
    }

    /**
     *
     * @param isInterleaved
     */
    public void setInterleaved(boolean isInterleaved) {
        this.interleaved = isInterleaved;
    }



    /**
     *
     * @return
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     *
     * @param authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     *
     * @return
     */
    public String getSongComment() {
        return songComment;
    }

    /**
     *
     * @param songComment
     */
    public void setSongComment(String songComment) {
        this.songComment = songComment;
    }

    

    /**
     *
     * @return
     */
    public String getSongName() {
        return songName;
    }

    /**
     *
     * @param songName
     */
    public void setSongName(String songName) {
        this.songName = songName;
    }

    

    /**
     *
     * @return
     */
    public int getFuturDataSize() {
        return futurDataSize;
    }

    /**
     *
     * @param futurDataSize
     */
    public void setFuturDataSize(int futurDataSize) {
        this.futurDataSize = futurDataSize;
    }

    /**
     *
     * @return
     */
    public int getLoopFrames() {
        return loopFrames;
    }

    /**
     *
     * @param loopFrames
     */
    public void setLoopFrames(int loopFrames) {
        this.loopFrames = loopFrames;
    }

    /**
     *
     * @return
     */
    public int getClock() {
        return clock;
    }

    /**
     *
     * @param clock
     */
    public void setClock(int clock) {
        this.clock = clock;
    }

    /**
     *
     * @return
     */
    public short getDigidrums() {
        return digidrums;
    }

    /**
     *
     * @param digidrums
     */
    public void setDigidrums(short digidrums) {
        this.digidrums = digidrums;
    }

    /**
     *
     * @return
     */
    public short getFrequency() {
        return frequency;
    }

    /**
     *
     * @param frequency
     */
    public void setFrequency(short frequency) {
        this.frequency = frequency;
    }

    /**
     *
     * @return
     */
    public int getFrames() {
        return frames;
    }

    /**
     *
     * @param frames
     */
    public void setFrames(int frames) {
        this.frames = frames;
    }

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getLeo() {
        return leo;
    }

    /**
     *
     * @param leo
     */
    public void setLeo(String leo) {
        this.leo = leo;
    }

    @Override
    public int getReplayRate()
    {
        return (int) getFrequency();
    }

    
}
