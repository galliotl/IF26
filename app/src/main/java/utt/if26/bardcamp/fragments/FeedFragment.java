package utt.if26.bardcamp.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import utt.if26.bardcamp.Interface.MusicClickListener;
import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.NewMusicActivity;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.db.Entity.Music;
import utt.if26.bardcamp.model.MusicData;
import utt.if26.bardcamp.viewModel.MusicViewModel;

import static android.app.Activity.RESULT_OK;

public class FeedFragment extends Fragment implements MusicClickListener {
    private final int REQUEST_CODE = 10;
    private MusicAdapter mAdapter;
    private List<MusicData> musicList;
    private MusicViewModel musicViewModel;
    private MainActivity mainActivity;
    private String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicViewModel = new MusicViewModel(Objects.requireNonNull(getActivity()).getApplication());
        username = getArguments() != null ? getArguments().getString("userId") : null;
        LiveData<List<MusicData>> musicData = musicViewModel.getFeed(username);
        musicData.observe(this, new Observer<List<MusicData>>() {
            @Override
            public void onChanged(List<MusicData> musicDatas) {
                musicList = musicDatas;
                mAdapter.setMusics(musicList);
            }
        });
        mainActivity = (MainActivity) getActivity();
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

        FloatingActionButton fab_music = rootView.findViewById(R.id.fab_new_music);
        fab_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewMusicActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra("title");
                String filePath = data.getStringExtra("filePath");
                if (title != null && filePath != null) {
                    Music music = new Music(title, username, filePath);
                    musicViewModel.insertMusic(music);
                    Toast.makeText(getContext(), "Music added!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onClick(View v, int position) {
        // Create a playlist composed from the element clicked to the end
        List<MusicData> playlist = new ArrayList<>();
        for (int i = position; i < musicList.size(); i++){
            playlist.add(musicList.get(i));
        }
        mainActivity.getMusicService().playFromNewPlaylist(playlist);
    }

    @Override
    public void onFavouriteClick(View v, int position) {
        MusicData music = musicList.get(position);
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

    @Override
    public boolean onLongPressed(View v, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Do you want to delete the song?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        musicViewModel.deleteMusic(musicList.get(position).id);
                        Toast.makeText(getContext(), "Music removed", Toast.LENGTH_SHORT).show();
                        musicList.remove(position);
                        mAdapter.setMusics(musicList);
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return true;
    }
}
