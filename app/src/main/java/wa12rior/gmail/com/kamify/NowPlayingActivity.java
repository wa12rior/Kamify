package wa12rior.gmail.com.kamify;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class NowPlayingActivity extends AppCompatActivity {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager mAudioManager;

    /**  information about playing */
    private boolean isPlaying;

    /** Information about touching seekbar */
    private boolean isTouching;

    /** Handler */
    private Handler mHandler = new Handler();

    /** icon resources */
    private int mPauseIcon;
    private int mPlayIcon;

    /** Image button */
    private ImageView play;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources
            // and changing icon
            play.setImageResource(mPlayIcon);
            mMediaPlayer.seekTo(0);
            isPlaying = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        mPauseIcon = R.drawable.ic_pause_circle_outline_black_24dp;
        mPlayIcon = R.drawable.ic_play_circle_outline_black_24dp;

        /** Seekbar */
        final SeekBar progress = (SeekBar) findViewById(R.id.progress);

        // library return button

        Button btn = (Button) findViewById(R.id.library_button);

//        play button
        play = (ImageView) findViewById(R.id.play_button);
        play.setClickable(true);
//        getting extra data from previous intent

        Intent mIntent = getIntent();
        int author = mIntent.getIntExtra("author", 0);
        int name = mIntent.getIntExtra("name", 0);
        int audio = mIntent.getIntExtra("audio", 0);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        ((TextView) findViewById(R.id.author)).setText(getResources().getString(author));
        ((TextView) findViewById(R.id.title)).setText(getResources().getString(name));

        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

            // Create and setup the {@link MediaPlayer} for the audio resource associated
            // with the current word
            mMediaPlayer = MediaPlayer.create(this, audio);

            progress.setMax(mMediaPlayer.getDuration());
            progress.setClickable(false);
            // Start the audio file
            mMediaPlayer.start();
            isPlaying = true;
            isTouching = false;

            progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    if (isTouching) {
                        mMediaPlayer.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    isTouching = true;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    isTouching = false;
                }
            });

            NowPlayingActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(mMediaPlayer != null){
                        int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                        progress.setProgress(mCurrentPosition);
                    }
                    mHandler.postDelayed(this, 1000);
                }
            });

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }

//        Handling pausing and resuming

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
//                    pausing the media player
                    isPlaying = false;
                    mMediaPlayer.pause();
//                    changing icon
                    ((ImageView) view).setImageResource(mPlayIcon);
                } else {
//                    resuming
                    isPlaying = true;
                    mMediaPlayer.start();
                    mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
//                    changing icon
                    ((ImageView) view).setImageResource(mPauseIcon);
                }
            }
        });

//        Setting click listener on library button to go back to the previous activity

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                releaseMediaPlayer();
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
