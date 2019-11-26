package utt.if26.bardcamp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import utt.if26.bardcamp.Interface.MusicClickListener;
import utt.if26.bardcamp.LoginActivity;
import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.db.Entity.User;
import utt.if26.bardcamp.model.MusicData;
import utt.if26.bardcamp.viewModel.LoginViewModel;
import utt.if26.bardcamp.viewModel.MusicViewModel;

public class AccountFragment extends Fragment implements MusicClickListener {

    private MusicAdapter mAdapter;
    private MusicViewModel musicViewModel;
    private LoginViewModel loginViewModel;
    private List<MusicData> musicList;
    private MainActivity mainActivity;
    private User currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String username = getArguments() != null ? getArguments().getString("userId") : null;

        musicViewModel = new MusicViewModel(Objects.requireNonNull(getActivity()).getApplication());
        loginViewModel = new LoginViewModel(Objects.requireNonNull(getActivity()).getApplication());

        if(username != null) {
            currentUser = loginViewModel.getUser(username);

            LiveData<List<MusicData>> musicData = musicViewModel.getFaved(currentUser.userName);
            musicData.observe(this, new Observer<List<MusicData>>() {
                @Override
                public void onChanged(List<MusicData> musicDatas) {
                    // Used for deletion and specific actions
                    musicList = musicDatas;
                    mAdapter.setMusics(musicList);
                }
            });
        }
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_fragment, container, false);

        // UI elements
        CircleImageView userAvatar = rootView.findViewById(R.id.account_avatar);
        TextView userName = rootView.findViewById(R.id.account_name);

        Button logoutButton = rootView.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.logout();
                Intent mainActivityIntent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), LoginActivity.class);
                startActivity(mainActivityIntent);
                getActivity().finish();
            }
        });

        if(currentUser != null) {
            // Update user
            RecyclerView recyclerView = rootView.findViewById(R.id.faved_list);
            mAdapter = new MusicAdapter(getContext(), this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Update user UI
            userName.setText(getString(R.string.name_field, currentUser.firstName, currentUser.lastName));
            Picasso.get().load(currentUser.picPath).into(userAvatar);

            // User deletion
            Button deleteUser = rootView.findViewById(R.id.deleteAccount);
            deleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setMessage("Are you sure you want to delete the account?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener(){

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(loginViewModel.deleteAccount(currentUser.userName)){
                                        Intent mainActivityIntent = new Intent(Objects.requireNonNull(getActivity()).getApplicationContext(), LoginActivity.class);
                                        startActivity(mainActivityIntent);
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getContext(), "Could not remove account", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    alertDialogBuilder.setNegativeButton("No",
                            null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } else {
            userName.setText(getString(R.string.name_field, "no", "user"));
        }

        FloatingActionButton fabCancel = rootView.findViewById(R.id.fab_new_music);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sssssss", "Clicked");
            }
        });
        return rootView;
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
        // We have to delete it from the list
        MusicData music = musicList.get(position);
        musicViewModel.deleteFav(music.id, currentUser.userName);
        music.fav = 0;
        musicList.remove(music);
        mAdapter.setMusics(musicList);
    }

    @Override
    public boolean onLongPressed(View v, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Do you want to remove it from favourites?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MusicData music = musicList.get(position);
                        musicViewModel.deleteFav(music.id, currentUser.userName);
                        music.fav = 0;
                        musicList.remove(music);
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
