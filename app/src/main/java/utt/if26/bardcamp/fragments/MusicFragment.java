package utt.if26.bardcamp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.util.DurationConverter;


public class MusicFragment extends Fragment {
    private TextView timer;
    private SeekBar seekBar;
    private int fullDuration = 0;
    private DurationConverter durationConverter = new DurationConverter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.music_fragment, container, false);
        TextView artistSongText = rootView.findViewById(R.id.artist_song_text);
        ImageButton playPause = rootView.findViewById(R.id.play_pause_btn);

        timer = rootView.findViewById(R.id.timer);
        ImageButton buton = rootView.findViewById(R.id.btn_next);
        ImageButton butoff = rootView.findViewById(R.id.btn_previous);

        seekBar = rootView.findViewById(R.id.advance);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!fromUser) return;
                //textview.setText(seekBar.getProgress()*music.media.getDuration()/100+" "+music.media.getCurrentPosition()+"/"+music.media.getDuration());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        } );
        return rootView;
    }

    private void updatePlayer(int currentDuration){
        timer.setText(String.format("%s / %s",
                durationConverter.milliSecondsToTimer((long) currentDuration),
                durationConverter.milliSecondsToTimer((long) fullDuration)));
    }
}
