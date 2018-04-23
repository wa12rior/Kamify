package wa12rior.gmail.com.kamify;

/**
 * Created by Friizza on 18.02.2018.
 *
 * {@link Song} represents the song which user wants to listen
 */

public class Song {

    /** String resource ID for the name of the song */
    private int mSongNameId;

    /** String resource ID for the author of the song */
    private int mSongAuthorId;

    /** String resource ID for the duration of the song */

    private int mDurationId;

    /** Audio resource ID for the word */
    private int mAudioResourceId;

    /**
     * Create a new Song Object
     *
     * @param songNameId - is the string resource Id for the song name
     * @param songAuthorId - is the string resource Id for the song author
     * @param audioResourceId - is the string resource Id for the audio resource
     */

    public Song(int songNameId, int songAuthorId, int durationId, int audioResourceId) {
        mSongAuthorId = songAuthorId;
        mAudioResourceId = audioResourceId;
        mSongNameId = songNameId;
        mDurationId = durationId;
    }

    /**
     * Return the audio resource ID of the song name.
     */

    public int getSongName() {
        return mSongNameId;
    }

    /**
     * Return the audio resource ID of the song name.
     */

    public int getDuration() {
        return mDurationId;
    }

    /**
     * Return the audio resource ID of the song author.
     */

    public int getSongAuthor() {
        return mSongAuthorId;
    }

    /**
     * Return the audio resource ID of the song.
     */

    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
