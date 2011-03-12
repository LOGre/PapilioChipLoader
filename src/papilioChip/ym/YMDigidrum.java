/*
 * YMDigidrum Class
 */

package papilioChip.ym;

/**
 * This class is there to model the embedded digidrums samples
 * @author shazz
 */
public class YMDigidrum {

    private int digidrumId;
    private int sampleSize;
    private byte[] sample;

    /**
     * Constructor
     */
    public YMDigidrum() {
    }


/**
     * dump the YM digidrums data
     */
    public void dump()
    {
        System.out.println("Digidrum " + getDigidrumId() + " sample size : " + getSampleSize());
    }

    /**
     * Get the digidrum id
     * @return the digidrum id
     */
    public int getDigidrumId() {
        return digidrumId;
    }

    /**
     * Set the digidrum id
     * @param digidrumId
     */
    public void setDigidrumId(int digidrumId) {
        this.digidrumId = digidrumId;
    }


    /**
     * Get the digidrum sample
     * @return the sample byte array
     */
    public byte[] getSample() {
        return sample;
    }

    /**
     * Set the digidrum sample
     * @param sample
     */
    public void setSample(byte[] sample) {
        this.sample = sample;
    }

    /**
     * Get the digidrum sample size
     * @return the sample size in bytes
     */
    public int getSampleSize() {
        return sampleSize;
    }

    /**
     * Set the digidrum sample size
     * @param sampleSize
     */
    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    

}
