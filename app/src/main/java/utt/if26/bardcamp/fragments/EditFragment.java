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
import utt.if26.bardcamp.models.User;

public class EditFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_account_fragment, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final EditText firstName = rootView.findViewById(R.id.edit_first_name);
        final EditText lastName = rootView.findViewById(R.id.edit_name);
        final EditText editPath = rootView.findViewById(R.id.edit_pic_path);

        if(mainActivity.getUser() != null) {
            User user = mainActivity.getUser();
            CircleImageView avatar = rootView.findViewById(R.id.edit_avatar);
            firstName.setText(mainActivity.getUser().getFirstName());
            lastName.setText(mainActivity.getUser().getLastName());
            editPath.setText(mainActivity.getUser().getPicPath());
            Picasso.get().load(user.getPicPath()).into(avatar);
        }


        FloatingActionButton fabCancel = rootView.findViewById(R.id.fab_close);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.loadFragment(new AccountFragment());
            }
        });

        Button validate = rootView.findViewById(R.id.validate_edit_btn);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getUser().setFirstName(firstName.getText().toString());
                mainActivity.getUser().setLastName(lastName.getText().toString());
                mainActivity.getUser().setPicPath(editPath.getText().toString());
                Toast.makeText(mainActivity, "Updated!", Toast.LENGTH_SHORT).show();
                mainActivity.loadFragment(new AccountFragment());
            }
        });
        return rootView;
    }
}