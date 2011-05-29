/*
 * SAP Header class
 */
package papilioChip.sap;

import papilioChip.Header;

/**
 *
 * @author shazz
 */
public class SAPHeader extends Header
{

    /**
     *
     */
    public final static String STARTSTRING = "SAP";
    /**
     *
     */
    public final static String AUTHOR = "AUTHOR";
    /**
     *
     */
    public final static String NAME = "NAME";
    /**
     *
     */
    public final static String DATE = "DATE";
    /**
     *
     */
    public final static String PLAYER = "PLAYER";
    /**
     *
     */
    public final static String MUSIC = "MUSIC";
    /**
     *
     */
    public final static String INIT = "INIT";
    /**
     *
     */
    public final static String SONGS = "SONGS";
    /**
     *
     */
    public final static String FASTPLAY = "FASTPLAY";
    /**
     *
     */
    public final static String DEFSONG = "DEFSONG";
    /**
     *
     */
    public final static String TYPE = "TYPE";
    /**
     *
     */
    public final static String STEREO = "STEREO";
    /**
     *
     */
    public final static String FRAMES = "FRAMES";

    /**
     *
     */
    public final static  char TYPE_R = 'R';
    /**
     *
     */
    public final static  char TYPE_C = 'C';
    /**
     *
     */
    public final static  char TYPE_B = 'B';
    /**
     *
     */
    public final static  char TYPE_S = 'S';
    /**
     *
     */
    public final static  char TYPE_D = 'D';
    
    /** Music author's name. */
    private String author = "No author";
    /** Music title. */
    private String name = " No name";
    /** Music creation date. */
    private String date = "No date";
    /** 1 for mono or 2 for stereo. */
    private int channels = 1;
    /** Number of subsongs. */
    private int songs = 0;
    /** 0-based index of the "main" subsong. */
    private int defaultSong = 0;

    /* player type */
    private char type = '?';

    /* frequency */
    private int fastplay;

    /* */
    private String musicAddress = "0000";
    private String initAddress = "0000";
    private String playerAddress = "0000";
    
    // frames nb
    int frames;

    /**
     *
     * @param stringEOL
     * @throws SAPProcessException
     */
    public void checkStartString(String stringEOL) throws SAPProcessException
    {
        if (!stringEOL.equals(STARTSTRING))
        {
            throw new SAPProcessException("Not a valid SAP file (Wrong start string)");
        }
    }

    /**
     *
     * @param stringEOL
     * @throws SAPProcessException
     */
    public void parseMetadata(String stringEOL) throws SAPProcessException
    {
        try
        {
            if (stringEOL.startsWith(SAPHeader.AUTHOR))
            {
                setAuthor(stringEOL.substring(stringEOL.indexOf('"')+1, stringEOL.lastIndexOf('"')));
            }
            else if (stringEOL.startsWith(SAPHeader.NAME))
            {
                setName(stringEOL.substring(stringEOL.indexOf('"')+1, stringEOL.lastIndexOf('"')));
            }
            else if (stringEOL.startsWith(SAPHeader.DATE))
            {
                setDate(stringEOL.substring(stringEOL.indexOf('"')+1, stringEOL.lastIndexOf('"')));
            }
            else if (stringEOL.startsWith(SAPHeader.PLAYER))
            {
                setPlayerAddress(stringEOL.substring(SAPHeader.PLAYER.length()+1));
            }
            else if (stringEOL.startsWith(SAPHeader.MUSIC))
            {
                setMusicAddress(stringEOL.substring(SAPHeader.MUSIC.length()+1));
            }
            else if (stringEOL.startsWith(SAPHeader.INIT))
            {
                setInitAddress(stringEOL.substring(SAPHeader.INIT.length()+1));
            }
            else if (stringEOL.startsWith(SAPHeader.SONGS))
            {
                setSongs(Integer.parseInt(stringEOL.substring(SAPHeader.SONGS.length()+1)));
            }
            else if (stringEOL.startsWith(SAPHeader.TYPE))
            {
                setType(stringEOL.substring(SAPHeader.TYPE.length()+1).charAt(0));             
            }
            else if (stringEOL.startsWith(SAPHeader.FASTPLAY))
            {
                setFastplay(Integer.parseInt(stringEOL.substring(SAPHeader.FASTPLAY.length()+1)));
            }
            else if (stringEOL.equals(SAPHeader.STEREO))
            {
                setChannels(2);
            }
            else if (stringEOL.startsWith(SAPHeader.FRAMES))
            {
                setFrames(Integer.parseInt(stringEOL.substring(SAPHeader.FRAMES.length()+1)));
            }
            else
            {
                System.out.println("Header not recognized => " + stringEOL);
            }
        }
        catch (NumberFormatException ex)
        {
            throw new SAPProcessException("Header value is not an integer", ex);
        }
    }

    /**
     *
     */
    public void dump()
    {
        System.out.println("---------------------------------------");
        System.out.println("AUTHOR: " + getAuthor());
        System.out.println("NAME : " + getName());
        System.out.println("DATE : " + getDate());
        System.out.println("TYPE : " + getType());
        System.out.println("FAST PLAY : " + getFastplay());
        System.out.println("FRAMES : " + getFrames());
        System.out.println("CHANNELS : " + getChannels());
        System.out.println("PLAYER : " + getPlayerAddress());
        System.out.println("INIT : " + getInitAddress());
        System.out.println("MUSIC : " + getMusicAddress());
        System.out.println("DEF SONG : " + getDefaultSong());
        System.out.println("SONGS : " + getSongs());

        System.out.println("---------------------------------------");
    }

    /**
     *
     * @return
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     *
     * @param author
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     *
     * @return
     */
    public int getChannels()
    {
        return channels;
    }

    /**
     *
     * @param channels
     */
    public void setChannels(int channels)
    {
        this.channels = channels;
    }

    /**
     *
     * @return
     */
    public String getDate()
    {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     *
     * @return
     */
    public int getDefaultSong()
    {
        return defaultSong;
    }

    /**
     *
     * @param defaultSong
     */
    public void setDefaultSong(int defaultSong)
    {
        this.defaultSong = defaultSong;
    }

    /**
     *
     * @return
     */
    public int getFastplay()
    {
        return fastplay;
    }

    /**
     *
     * @param fastplay
     */
    public void setFastplay(int fastplay)
    {
        this.fastplay = fastplay;
    }

    /**
     *
     * @return
     */
    public String getInitAddress()
    {
        return initAddress;
    }

    /**
     *
     * @param initAddress
     */
    public void setInitAddress(String initAddress)
    {
        this.initAddress = initAddress;
    }

    /**
     *
     * @return
     */
    public String getMusicAddress()
    {
        return musicAddress;
    }

    /**
     *
     * @param musicAddress
     */
    public void setMusicAddress(String musicAddress)
    {
        this.musicAddress = musicAddress;
    }

    /**
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getPlayerAddress()
    {
        return playerAddress;
    }

    /**
     *
     * @param playerAddress
     */
    public void setPlayerAddress(String playerAddress)
    {
        this.playerAddress = playerAddress;
    }

    /**
     *
     * @return
     */
    public int getSongs()
    {
        return songs;
    }

    /**
     *
     * @param songs
     */
    public void setSongs(int songs)
    {
        this.songs = songs;
    }

    /**
     *
     * @return
     */
    public char getType()
    {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(char type)
    {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public int getFrames()
    {
        return frames;
    }

    /**
     *
     * @param frames
     */
    public void setFrames(int frames)
    {
        this.frames = frames;
    }

    /**
     *
     * @return
     */
    @Override
    public int getReplayRate()
    {
        int rate = (int)((50.0f*(float)fastplay)/312.0f);
        return rate;
    }

    /**
     *
     * @return
     */
    @Override
    public int getLoopFrames()
    {
        return 0;
    }

    

}
