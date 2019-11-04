package utt.if26.bardcamp.adapter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.bdd.AppDB;
import utt.if26.bardcamp.bdd.AppDBTable;
import utt.if26.bardcamp.models.Music;
import utt.if26.bardcamp.models.User;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private Cursor mCursor;
    private User user;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView artist;
        public ImageView avatar;
        public ImageButton favourite;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.music_title);
            artist = v.findViewById(R.id.music_artist);
            avatar = v.findViewById(R.id.music_avatar);
            favourite = v.findViewById(R.id.music_favourite);
        }
    }

    public MusicAdapter(Cursor myDataset, User user) {
        swapCursor(myDataset);
        this.user = user;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_layout,parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SQLiteDatabase db = new AppDB(holder.itemView.getContext()).getWritableDatabase();
        mCursor.moveToPosition(position);

        final Music music = new Music(
                mCursor.getInt(mCursor.getColumnIndexOrThrow(AppDBTable.Music._ID)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.User.COLUMN_FIRSTNAME))+" "+mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.User.COLUMN_NAME)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.Music.COLUMN_TITLE)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.Music.COLUMN_PATH)),
                mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.Music.COLUMN_PIC_PATH)),
                mCursor.getInt(mCursor.getColumnIndexOrThrow(AppDBTable.Favourite.ID_ALIAS))
        );

        Picasso.get().load(music.getPicPath()).into(holder.avatar);
        holder.artist.setText(music.getArtistName());
        holder.title.setText(music.getTitle());
        holder.favourite.setImageResource(music.isFavourite() ? R.drawable.favorite_24dp : R.drawable.favorite_border_24dp);

        // click listener for favIcon
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music.isFavourite()) {
                    db.delete(
                            AppDBTable.Favourite.TABLE_NAME,
                            AppDBTable.Favourite._ID+"=?",
                            new String[] {String.valueOf(music.getFavourite())}
                    );
                    music.setFavourite(0);
                    Toast.makeText(v.getContext(), "removed from favourites", Toast.LENGTH_SHORT).show();
                    holder.favourite.setImageResource(R.drawable.favorite_border_24dp);
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(AppDBTable.Favourite.COLUMN_USER, user.getId());
                    cv.put(AppDBTable.Favourite.COLUMN_MUSIC, music.getID());
                    int id = Double.valueOf(db.insert(AppDBTable.Favourite.TABLE_NAME, null, cv)).intValue();
                    music.setFavourite(id);
                    Toast.makeText(v.getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                    holder.favourite.setImageResource(R.drawable.favorite_24dp);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "music will be played", Toast.LENGTH_SHORT).show();
                // TODO: play the music
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if(this.mCursor != null) mCursor.close();
        mCursor = newCursor;
        if(this.mCursor != null) notifyDataSetChanged();
    }
}
