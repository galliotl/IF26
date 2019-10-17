package utt.if26.bardcamp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.models.Music;

import static android.content.ContentValues.TAG;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Music music = mDataset.get(position);
        Picasso.get().load(music.getPicPath()).into(holder.avatar);
        holder.artist.setText(music.getArtistName());
        holder.title.setText(music.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
