/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package papilioChip;

import java.io.IOException;
import papilioChip.ym.YMHeader;
import java.util.Vector;
import jssc.*;
import papilioChip.sap.SAPHeader;
import papilioChip.sap.SAPLoader;
import papilioChip.ym.YMLoader;

import joptsimple.*;
import static java.util.Arrays.*;

/**
 *
 * @author admin
 */
public class JsscSerialLoader
{

    private SerialPort serialPort;
    private static long fixedDelay = 0;

    /**
     * Main class, startup
     * @param args
     */
    public static void main(String[] args)
    {
        // Check the passed arguments
        String fileToDepack = "";
        String port = "";
        int uartFreq = 0;
        boolean dumpFrames = false;

        try
        {
            OptionSet options = null;
            OptionParser parser = new OptionParser()
            {
                {
                    acceptsAll(asList("f", "file"), "filename").withRequiredArg().ofType(String.class).describedAs("*.ym, *.sap");
                    acceptsAll(asList("p", "port"), "port name").withRequiredArg().ofType(String.class).describedAs("serial port");
                    acceptsAll(asList("b", "baudrate"), "(opt) baudrate").withOptionalArg().ofType(Integer.class).defaultsTo(1000000);
                    acceptsAll(asList("d", "dump"), "(opt) dump frames on screen");
                    acceptsAll(asList("t", "tempo"), "(opt) sleep delay").withRequiredArg().ofType(Long.class);
                    acceptsAll(asList("h", "?"), "(opt) show help");
                }
            };
            try
            {
                options = parser.parse(args);
                if (options.has("?"))
                {
                    parser.printHelpOn(System.out);
                    System.exit(1);
                }
                if (options.has("file"))
                {
                    fileToDepack = (String) options.valueOf("file");
                }
                else
                {
                    throw new Exception("Missing --file argument");
                }
                if (options.has("port"))
                {
                    port = (String) options.valueOf("port");
                }
                else
                {
                    throw new Exception("Missing --port argument");
                }
                if (options.has("baudrate"))
                {
                    uartFreq = (Integer) options.valueOf("baudrate");
                }
                if (options.has("tempo"))
                {
                    fixedDelay = (Long) options.valueOf("tempo");
                }
                if (options.has("dump"))
                {
                    dumpFrames = true;
                }

            }
            catch (Exception ex)
            {
                System.err.println(ex.getMessage());
                parser.printHelpOn(System.out);
                System.exit(1);
            }
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }

        // Arguments parsed, let's depack and stream the YM dump now
        try
        {
            // init the serial loader
            System.out.println("Init serial port");
            JsscSerialLoader serialLoader = new JsscSerialLoader();

            // connect the port at the good frequency
            System.out.println("Connect serial port : " + port + " at " + uartFreq + " bauds");
            serialLoader.connect(port, uartFreq);

            // Depack and display header & dump on screen
            System.out.println("Depacking : " + fileToDepack);

            String ext = fileToDepack.substring(fileToDepack.lastIndexOf('.') + 1);
            Loader loader = null;
            Header header;

            if (ext.equalsIgnoreCase(Header.SAPFILEEXT))
            {
                loader = new SAPLoader();
                loader.depack(fileToDepack);
                header = (SAPHeader) loader.decodeFileFormat();
            }
            else if (ext.equalsIgnoreCase(Header.YMFILEEXT))
            {
                loader = new YMLoader();
                loader.depack(fileToDepack);
                header = (YMHeader) loader.decodeFileFormat();
                //loader.dumpDigiDrums();
            }
            else
            {
                throw new ProcessException("Format not (yet ?) managed : " + ext);
            }

            header.dump();
            if (dumpFrames)
            {
                loader.dumpFrames();
            }


            // stream the data to the serial port
            //if(tempo > 0)
            //    System.out.println("Sending on port " + port + " at " + 1.0f/((float)tempo/1000.0f) + " Hz");
            //else
            System.out.println("Sending on port " + port);
            serialLoader.stream(loader.getFramesBuffer(), header);

            // disconnect the port
            System.out.println("Stream ended, disconnecting...");
            serialLoader.disconnect();

            //bye bye
            System.out.println("Done, exiting");
        }
        catch (ProcessException ex)
        {
            System.err.println("FATAL : " + ex.getMessage());
            System.exit(1);
        }
        catch (SerialProcessException ex)
        {
            System.err.println("FATAL : " + ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Constructor
     *
     * @throws SerialProcessException
     */
    public JsscSerialLoader() throws SerialProcessException
    {
        //Properties props = System.getProperties();
        //String jlp = props.getProperty("java.library.path");
        //props.setProperty("java.library.path", "/home/alain/tools/rxtx/rxtx-2.1-7-bins-r2/Linux/i686-unknown-linux-gnu");
        //System.setProperties(props);
        System.out.println("Check available port(s) :");
        String[] portNames = SerialPortList.getPortNames();

        for (int i = 0; i < portNames.length; i++)
        {
            System.out.println("[" + i + "] " + portNames[i]);
        }

    }

    /**
     * Disconnect the serial port
     * @throws SerialProcessException
     */
    public void disconnect() throws SerialProcessException
    {
        try
        {
            // close everything
            serialPort.closePort();
        }
        catch (SerialPortException ex)
        {
            throw new SerialProcessException("Cannot close the serial port", ex);
        }
    }

    /**
     * Stream a YMBuffer thru the serial port according to the dump frequency
     * @param buffer
     * @param frequency
     * @throws SerialProcessException
     */
    public void stream(FramesBuffer buffer, Header header) throws SerialProcessException
    {
        if (this.serialPort == null)
        {
            throw new SerialProcessException("Serial Connection not set");
        }

        //SerialPortEmu serialPortEmu = new SerialPortEmu();    
        // delay to fit the YM dump frequency (usually 50Hz)
        long delay = 0;
        if (fixedDelay == 0)
        {
            delay = (long) ((1 / (float) header.getReplayRate() * 1000));
        }
        else
        {
            delay = fixedDelay;
        }
        System.out.println("Sending " + buffer.getRegistersNb() + " registers (total: " + buffer.getFramesNb() + " frames) every " + delay + " ms (" + header.getReplayRate() + " Hz)");

        try
        {
            long startTime, elapsedTime;
            byte[] regs;

            Vector buf = buffer.getFramesData();

            long sleeptime = 0;
            for (int frames = 0; frames < buffer.getFramesNb(); frames++)
            {
                // send a full frame
                startTime = System.nanoTime();
                regs = (byte[]) (buf.get(frames));
                serialPort.writeBytes(regs);
                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                sleeptime = delay - elapsedTime;

                if (sleeptime > 0)
                {
                    Thread.sleep(sleeptime);
                } //System.out.println("We had to wait " + (delay - elapsedTime) + " ms");
            }
            System.out.println("end before loop");

            // manage loop
            if (header.getLoopFrames() > 0)
            {
                while (true)
                {
                    for (int frames = header.getLoopFrames(); frames
                            < buffer.getFramesNb(); frames++)
                    {
                        startTime = System.nanoTime();
                        regs = (byte[]) (buf.get(frames));
                        serialPort.writeBytes(regs);
                        elapsedTime = (System.nanoTime() - startTime) / 1000000;

                        if (delay - elapsedTime > 0)
                        {
                            Thread.sleep(delay - elapsedTime);
                        }
                    }
                }
            }
        }
        catch (InterruptedException ex)
        {
            throw new SerialProcessException(ex.getMessage(), ex);
        }
        catch (SerialPortException ex)
        {
            throw new SerialProcessException(ex.getMessage(), ex);
        }
    }

    /**
     * Connect to the serial port at the given baud rate
     * @param portName
     * @param uartFreq
     * @throws SerialProcessException
     */
    public void connect(String portName, int uartFreq) throws SerialProcessException
    {
        try
        {
            serialPort = new SerialPort(portName);
            serialPort.openPort();
            serialPort.setParams(uartFreq,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            //System.out.println("Connected to port " + portName + " at " + uartFreq + " bauds");

        }
        catch (SerialPortException ex)
        {
            throw new SerialProcessException(ex.getMessage(), ex);
        }
    }
}
