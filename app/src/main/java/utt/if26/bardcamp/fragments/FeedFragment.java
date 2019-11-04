package utt.if26.bardcamp.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.bdd.AppDB;
import utt.if26.bardcamp.bdd.AppDBTable;
import utt.if26.bardcamp.models.User;

public class FeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);

        user = ((MainActivity) getActivity()).getUser();
        if(user != null) {
            recyclerView = rootView.findViewById(R.id.feed_list);
            mAdapter = new MusicAdapter(fetchMusicsFromDB(), user);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }

    private Cursor fetchMusicsFromDB() {
        SQLiteDatabase database = new AppDB(getContext()).getReadableDatabase();
        String query = "SELECT"
                // Projection
                + " m."+AppDBTable.Music._ID
                + ", m."+AppDBTable.Music.COLUMN_ARTIST
                + ", m."+AppDBTable.Music.COLUMN_TITLE
                + ", m."+AppDBTable.Music.COLUMN_PIC_PATH
                + ", m."+AppDBTable.Music.COLUMN_PATH
                + ", u."+AppDBTable.User.COLUMN_FIRSTNAME
                + ", u."+AppDBTable.User.COLUMN_NAME
                + ", f."+AppDBTable.Favourite._ID+" as "+ AppDBTable.Favourite.ID_ALIAS
                // Table name
                + " FROM "+AppDBTable.Music.TABLE_NAME+" m"
                // Left join
                + " LEFT OUTER JOIN "+AppDBTable.User.TABLE_NAME+" u"
                + " ON m."+AppDBTable.Music.COLUMN_ARTIST+"=u." + AppDBTable.User._ID
                + " LEFT OUTER JOIN"
                + " (SELECT * FROM "+AppDBTable.Favourite.TABLE_NAME
                + " WHERE "+AppDBTable.Favourite.COLUMN_USER+"=?) f"
                + " ON f."+AppDBTable.Favourite.COLUMN_MUSIC+"=m."+AppDBTable.Music._ID;
        Cursor cursor = database.rawQuery(query,new String[] {(user != null ? String.valueOf(user.getId()) : "0")});

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
