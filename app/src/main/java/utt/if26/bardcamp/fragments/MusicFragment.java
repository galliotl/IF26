package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.models.Music;

public class MusicFragment extends Fragment {

    private Music music;

    public MusicFragment(Music music) {
        this.music = music;
    }
    public MusicFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.music_fragment, container, false);

        TextView artistSongText = getActivity().findViewById(R.id.artist_song_text);
        if(music != null) {
            artistSongText.setText(getString(R.string.song_playing, music.getArtistName(), music.getTitle()));
        }
        return rootView;
    }
}
