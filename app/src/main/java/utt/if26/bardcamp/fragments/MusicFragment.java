package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import utt.if26.bardcamp.R;

public class MusicFragment extends Fragment {

    public MusicFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.music_fragment, container, false);

        TextView artistSongText = rootView.findViewById(R.id.artist_song_text);
        ImageButton playPause = rootView.findViewById(R.id.play_pause_btn);

        playPause.setOnClickListener(
                new View.OnClickListener() {
                    boolean play = true;
                    @Override
                    public void onClick(View view) {
                        if(play) {
                            play = false;
                            ((ImageButton) view).setImageResource(R.drawable.play_72dp);
                        } else {
                            play = true;
                            ((ImageButton) view).setImageResource(R.drawable.pause_72dp);
                        }
                    }
                }
        );

        return rootView;
    }
}
