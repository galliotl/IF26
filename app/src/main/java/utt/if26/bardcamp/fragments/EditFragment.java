package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;

public class EditFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_account_fragment, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final EditText firstName = rootView.findViewById(R.id.edit_first_name);
        final EditText lastName = rootView.findViewById(R.id.edit_name);
        final EditText editPath = rootView.findViewById(R.id.edit_pic_path);
        return rootView;
    }
}
