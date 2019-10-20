package utt.if26.bardcamp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.models.Music;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<Music> mDataset;

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

    public MusicAdapter(List<Music> myDataset) {
        mDataset = myDataset;
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
        final Music music = mDataset.get(position);
        Picasso.get().load(music.getPicPath()).into(holder.avatar);
        holder.artist.setText(music.getArtistName());
        holder.title.setText(music.getTitle());
        holder.favourite.setImageResource(music.isFavourite() ? R.drawable.favorite_24dp : R.drawable.favorite_border_24dp);

        // click listener for favIcon
        holder.favourite.setOnClickListener(new View.OnClickListener() {
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
        return mDataset.size();
    }
}
