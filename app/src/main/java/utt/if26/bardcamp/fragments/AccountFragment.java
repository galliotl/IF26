package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.models.Music;
import utt.if26.bardcamp.models.User;

public class AccountFragment extends Fragment {
    private ArrayList<Music> musics = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MainActivity mainActivity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.account_fragment, container, false);

        TextView name = rootView.findViewById(R.id.account_name);

        if(mainActivity.getUser() != null) {
            User user = mainActivity.getUser();
            name.setText(getString(R.string.name_field, user.getFirstName(), user.getLastName()));
            CircleImageView avatar = rootView.findViewById(R.id.account_avatar);
            Picasso.get().load(user.getPicPath()).into(avatar);
        } else {
            name.setText(getString(R.string.name_field, "default", "name"));
        }

        FloatingActionButton fabCancel = rootView.findViewById(R.id.fab_edit);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.loadFragment(new EditFragment());
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.faved_list);

        RecyclerView.Adapter<MusicAdapter.ViewHolder> mAdapter = new MusicAdapter(musics);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        generateMusics();
        mAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void generateMusics(){
        musics.add(new Music("Artist1", "song1", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist2", "song2", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist3", "song3", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist4", "song4", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
        musics.add(new Music("Artist1", "song1", "../../sjj.mp3", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Music-icon.png"));
    }
}
