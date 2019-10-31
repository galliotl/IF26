package utt.if26.bardcamp.adapter;

import android.database.Cursor;
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
import utt.if26.bardcamp.bdd.AppDBTable;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private Cursor mCursor;

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

    public MusicAdapter(Cursor myDataset) {
        mCursor = myDataset;
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
        if(!mCursor.move(position)) return;

        String picPath = mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.Music.COLUMN_PIC_PATH));
        String displayName = mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.User.COLUMN_FIRSTNAME))
                + " " + mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.User.COLUMN_NAME));
        String title = mCursor.getString(mCursor.getColumnIndexOrThrow(AppDBTable.Music.COLUMN_TITLE));
        boolean fav = true;
        if(mCursor.getInt(mCursor.getColumnIndexOrThrow(AppDBTable.Favourite._ID)) == 0) fav = false;

        Picasso.get().load(picPath).into(holder.avatar);
        holder.artist.setText(displayName);
        holder.title.setText(title);
        holder.favourite.setImageResource(fav ? R.drawable.favorite_24dp : R.drawable.favorite_border_24dp);

        // click listener for favIcon
        /*holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music.isFavourite()) {
                    Toast.makeText(v.getContext(), "removed from favourites", Toast.LENGTH_SHORT).show();
                    music.setFavourite(false);
                    holder.favourite.setImageResource(R.drawable.favorite_border_24dp);
                } else {
                    Toast.makeText(v.getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                    music.setFavourite(true);
                    holder.favourite.setImageResource(R.drawable.favorite_24dp);
                }
            }
        });
        */
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
