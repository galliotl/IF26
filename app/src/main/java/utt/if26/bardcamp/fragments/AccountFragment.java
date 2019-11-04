package utt.if26.bardcamp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.bdd.AppDB;
import utt.if26.bardcamp.bdd.AppDBTable;
import utt.if26.bardcamp.models.User;

public class AccountFragment extends Fragment {
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MainActivity mainActivity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.account_fragment, container, false);

        TextView name = rootView.findViewById(R.id.account_name);
        user = mainActivity.getUser();
        if(user != null) {
            user = mainActivity.getUser();
            name.setText(getString(R.string.name_field, user.getFirstName(), user.getLastName()));
            CircleImageView avatar = rootView.findViewById(R.id.account_avatar);
            Picasso.get().load(user.getPicPath()).into(avatar);

            RecyclerView recyclerView = rootView.findViewById(R.id.faved_list);

            RecyclerView.Adapter<MusicAdapter.ViewHolder> mAdapter = new MusicAdapter(fetchMusicsFromDB(), user);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
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
        return rootView;
    }
    private Cursor fetchMusicsFromDB() {
        SQLiteDatabase database = new AppDB(getContext()).getReadableDatabase();
        String query = "SELECT"
                // Projection
                + " m."+ AppDBTable.Music._ID
                + ", m."+AppDBTable.Music.COLUMN_ARTIST
                + ", m."+AppDBTable.Music.COLUMN_TITLE
                + ", m."+AppDBTable.Music.COLUMN_PIC_PATH
                + ", m."+AppDBTable.Music.COLUMN_PATH
                + ", u."+AppDBTable.User.COLUMN_FIRSTNAME
                + ", u."+AppDBTable.User.COLUMN_NAME
                + ", f."+AppDBTable.Favourite._ID+" as "+ AppDBTable.Favourite.ID_ALIAS
                // Table name
                + " FROM "+AppDBTable.Favourite.TABLE_NAME+" f"
                // Left join
                + " LEFT OUTER JOIN "+AppDBTable.User.TABLE_NAME+" u"
                + " ON f."+AppDBTable.Favourite.COLUMN_USER+"=u." + AppDBTable.User._ID
                + " LEFT OUTER JOIN "+AppDBTable.Music.TABLE_NAME+" m"
                + " ON f."+AppDBTable.Favourite.COLUMN_MUSIC+"=m."+AppDBTable.Music._ID
                // Where clause
                + " WHERE f."+AppDBTable.Favourite.COLUMN_USER+"=?";
        Cursor cursor = database.rawQuery(query, new String[] {((MainActivity)getActivity()).getUser() != null ? String.valueOf(((MainActivity)getActivity()).getUser().getId()) : "0"});

        /*Log.d("DB", query + "############################# " + cursor.getCount());
        for(int i = 0; i<cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Log.d("#####SEPARATOR", "###########################################");
            for(String col: cursor.getColumnNames()) {
                Log.d("#####", col + " -> " + cursor.getString(cursor.getColumnIndexOrThrow(col)));
            }
        }*/
        return cursor;
    }
}
