/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package papilioChip.ym.emulator;

/**
 *
 * @author admin
 */
public class SerialPortEmu implements Runnable {

    private AY_3_8910 ym;
    private boolean isRunning = false;

    public SerialPortEmu() {
        ym = new AY_3_8910();
        this.init();
    }

    public void receive(byte[] registersDump)
    {
        for (int i = 0; i < registersDump.length; i++) {
            //System.out.println("Receiving " + Integer.toHexString(0x100 | (registersDump[i] & 0xFF )).substring(1).toUpperCase() + " for reg " + i);
            ym.setRegister(i, (registersDump[i] & 0xFF));
        }
        
    }

    public void init()
    {
        if (!isRunning)
        {
            isRunning = true;
            Thread thread = new Thread(this);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();

            System.out.println("thread started");
        }
    }

    public void stop()
    {
        if (isRunning)
        {
            isRunning = false;
        }
    }

    public void run()
    {
        while (isRunning)
        {
            try
            {
                ym.writeAudio();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
