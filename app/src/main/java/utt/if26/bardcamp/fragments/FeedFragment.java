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

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.adapter.MusicAdapter;
import utt.if26.bardcamp.bdd.AppDB;
import utt.if26.bardcamp.bdd.AppDBTable;

public class FeedFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.feed_list);

        mAdapter = new MusicAdapter(fetchMusicsFromDB());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private Cursor fetchMusicsFromDB() {
        SQLiteDatabase database = new AppDB(getContext()).getReadableDatabase();

        String query = "SELECT * FROM " + AppDBTable.Music.TABLE_NAME +
                " LEFT JOIN " + AppDBTable.User.TABLE_NAME +
                " ON user." + AppDBTable.User._ID + "=music." + AppDBTable.Music.COLUMN_ARTIST +
                " LEFT JOIN (SELECT * FROM "+ AppDBTable.Favourite.TABLE_NAME +
                    " WHERE " + AppDBTable.Favourite.COLUMN_USER + "=1" + ") as fav" + // TODO: replace 1 with user.id
                " ON fav." + AppDBTable.Favourite.COLUMN_MUSIC + "=music." + AppDBTable.Music._ID;

        Log.d("DB",query);

        return database.rawQuery(query,null);
    }
}
