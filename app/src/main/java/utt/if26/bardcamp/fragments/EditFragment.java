package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;

public class EditFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_account_fragment, container, false);
        FloatingActionButton fabCancel = rootView.findViewById(R.id.fab_close);
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment(new AccountFragment());
            }
        });
        return rootView;
    }
}
