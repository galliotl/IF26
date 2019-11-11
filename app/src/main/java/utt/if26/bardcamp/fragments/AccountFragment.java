package utt.if26.bardcamp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import utt.if26.bardcamp.Interface.MusicClickListener;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.db.Entity.User;
import utt.if26.bardcamp.model.MusicUI;
import utt.if26.bardcamp.util.LoginResult;
import utt.if26.bardcamp.viewModel.LoginViewModel;
import utt.if26.bardcamp.viewModel.MusicViewModel;

public class AccountFragment extends Fragment implements MusicClickListener {

    private MusicAdapter mAdapter;
    private MusicViewModel musicViewModel;
    private LoginViewModel loginViewModel;
    private LiveData<List<MusicUI>> musicData;
    private List<MusicUI> musicList;
    private LiveData<LoginResult> loginResultLiveData;
    private User currentUser;

    // UI elements
    private CircleImageView userAvatar;
    private TextView userName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicViewModel = new MusicViewModel(getActivity().getApplication());
        musicData = musicViewModel.getFaved();
        musicData.observe(this, new Observer<List<MusicUI>>() {
            @Override
            public void onChanged(List<MusicUI> musicUIS) {
                musicList = musicUIS;
                mAdapter.setMusics(musicList);
            }
        });

        loginViewModel = new LoginViewModel(getActivity().getApplication());
        loginResultLiveData = loginViewModel.getLoginResult();
        loginResultLiveData.observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if(loginResult.getSuccess() != null) {
                    currentUser = loginResult.getSuccess();
                    Log.d("#######", currentUser.toString());
                    updateUserView();
                }
            }
        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_fragment, container, false);

        userAvatar = rootView.findViewById(R.id.account_avatar);
        userName = rootView.findViewById(R.id.account_name);

        updateUserView();

        RecyclerView recyclerView = rootView.findViewById(R.id.faved_list);

        mAdapter = new MusicAdapter(getContext(), this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fabCancel = rootView.findViewById(R.id.fab_edit);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sssssss", "Clicked");
            }
        });
        return rootView;
    }

    public void updateUserView(){
        if(userName != null && userAvatar != null) {
            if(currentUser != null) {
                Log.d("#########", currentUser.toString());

                userName.setText(getString(R.string.name_field, currentUser.firstName, currentUser.lastName));
                Picasso.get().load(currentUser.picPath).into(userAvatar);
            } else {
                userName.setText(getString(R.string.name_field, "default", "name"));
            }
        }
    }

    @Override
    public void onClick(View v, int position) {
        Toast.makeText(v.getContext(), "music will be played", Toast.LENGTH_SHORT).show();
        // TODO: play the music
    }

    @Override
    public void onFavouriteClick(View v, int position) {
        // We have to delete it from the list
        MusicUI music = musicList.get(position);
        musicViewModel.deleteFav(music.id);
        music.fav = 0;
        musicList.remove(music);
        mAdapter.setMusics(musicList);
    }
}
