package utt.if26.bardcamp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import utt.if26.bardcamp.Interface.MusicClickListener;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.model.MusicUI;
import utt.if26.bardcamp.viewModel.MusicViewModel;

public class FeedFragment extends Fragment implements MusicClickListener {
    private MusicAdapter mAdapter;
    private List<MusicUI> musicList;
    private MusicViewModel musicViewModel;
    private String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicViewModel = new MusicViewModel(Objects.requireNonNull(getActivity()).getApplication());
        username = getArguments() != null ? getArguments().getString("userId") : null;
        LiveData<List<MusicUI>> musicData = musicViewModel.getFeed(username);
        musicData.observe(this, new Observer<List<MusicUI>>() {
            @Override
            public void onChanged(List<MusicUI> musicUIS) {
                musicList = musicUIS;
                mAdapter.setMusics(musicList);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.feed_list);
        mAdapter = new MusicAdapter(this.getContext(), this);
        mAdapter.setMusics(musicList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onClick(View v, int position) {
        Toast.makeText(v.getContext(), "music will be played", Toast.LENGTH_SHORT).show();
        // TODO: play the music
    }

    @Override
    public void onFavouriteClick(View v, int position) {
        MusicUI music = musicList.get(position);
        if(music.fav != 0) {
            musicViewModel.deleteFav(music.id, username);
            music.fav = 0;
            ((ImageButton) v).setImageResource(R.drawable.favorite_border_24dp);
        } else {
            musicViewModel.insertFav(music.id, username);
            music.fav = 1;
            ((ImageButton) v).setImageResource(R.drawable.favorite_24dp);
        }
    }
}
