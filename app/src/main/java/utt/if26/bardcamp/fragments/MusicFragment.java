package utt.if26.bardcamp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.model.MusicData;
import utt.if26.bardcamp.util.DurationConverter;


// TODO: try to use a better communication process between fragment and service
public class MusicFragment extends Fragment {
    private TextView timer;
    private SeekBar seekBar;
    private int fullDuration;
    private DurationConverter durationConverter = new DurationConverter();
    private MainActivity mainActivity;
    private boolean isPlaying;
    private MusicData currentMusic;
    private TextView artistSongText;
    private ImageButton playPause;
    private ImageButton btnNext;
    private ImageButton btnPrevious;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        setUpDataListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.music_fragment, container, false);

        // set the views
        artistSongText = rootView.findViewById(R.id.artist_song_text);
        timer = rootView.findViewById(R.id.timer);
        seekBar = rootView.findViewById(R.id.advance);
        playPause = rootView.findViewById(R.id.play_pause_btn);
        btnNext = rootView.findViewById(R.id.btn_next);
        btnPrevious = rootView.findViewById(R.id.btn_previous);

        // Placeholders and default values
        setUpDefaultValues();

        // setListeners
        setupViewListeners();
        return rootView;
    }

    private void setUpDataListener() {
        mainActivity.getMusicService().getCurrentMusicLiveData().observe(this, new Observer<MusicData>() {
            @Override
            public void onChanged(MusicData musicData) {
                currentMusic = musicData;
                fullDuration = mainActivity.getMusicService().getMusicDuration();
                updatePlayer();
            }
        });

        mainActivity.getMusicService().isPlayingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                isPlaying = aBoolean;
                playPause.setImageResource(aBoolean ? R.drawable.pause_72dp : R.drawable.play_72dp);
            }
        });
    }

    private void setUpDefaultValues() {
        artistSongText.setText("No music");
        playPause.setImageResource(R.drawable.play_72dp);
        seekBar.setMax(0);
        seekBar.setMax(100);
    }

    private void setupViewListeners() {
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMusic == null) {
                    Toast.makeText(getContext(), "You have to choose a music from the feed", Toast.LENGTH_SHORT).show();
                } else {
                    if(isPlaying) mainActivity.getMusicService().pauseMedia();
                    else mainActivity.getMusicService().resumeMedia();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getMusicService().playNext();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getMusicService().playPrevious();
            }
        });

        // TODO: figure out
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!fromUser) return;
                mainActivity.getMusicService().seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        } );

    }

    // TODO: make it a live data and run it in the music service, it makes more sense
    private Runnable mUpdateTime = new Runnable() {
        public void run() {
            int currentDuration = mainActivity.getMusicService().getCurrentMusicTimer();
            updateTimer(currentDuration);
            seekBar.setProgress((int)(((float)currentDuration/fullDuration)*100));
            timer.postDelayed(this, 1000);
        }
    };

    private void updateTimer(int currentDuration){
        timer.setText(String.format("%s / %s",
                durationConverter.milliSecondsToTimer((long) currentDuration),
                durationConverter.milliSecondsToTimer((long) fullDuration)));
    }

    private void updatePlayer() {
        artistSongText.setText(currentMusic.toString());
        timer.post(mUpdateTime);
    }
}
