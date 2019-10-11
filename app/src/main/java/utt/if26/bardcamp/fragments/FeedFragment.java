package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.models.Music;

public class FeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);
        ListView mListView = rootView.findViewById(R.id.feed_listview);
        List<Music> musics = genererMusics();

        MusicAdapter adapter = new MusicAdapter(getActivity(),musics);
        mListView.setAdapter(adapter);

        return rootView;
    }

    private List<Music> genererMusics(){
        List<Music> musics = new ArrayList<Music>();
        musics.add(new Music("Artist1", "song1", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist2", "song2", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist3", "song3", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist4", "song4", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        return musics;
    }
}
