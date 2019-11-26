package utt.if26.bardcamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import utt.if26.bardcamp.Interface.MusicClickListener;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.model.MusicData;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private final LayoutInflater mInflater;
    private List<MusicData> musics;
    private MusicClickListener mcl;

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView artist;
        public ImageView avatar;
        public ImageButton favourite;

        public MusicViewHolder(final View v) {
            super(v);
            title = v.findViewById(R.id.music_title);
            artist = v.findViewById(R.id.music_artist);
            avatar = v.findViewById(R.id.music_avatar);
            favourite = v.findViewById(R.id.music_favourite);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mcl.onClick(view, getAdapterPosition());
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    return mcl.onLongPressed(view, getAdapterPosition());
                }
            });

            favourite.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    mcl.onFavouriteClick(view, getAdapterPosition());
                }
            });
        }
    }

    public MusicAdapter(Context context, MusicClickListener mcl) {
        mInflater = LayoutInflater.from(context);
        this.mcl = mcl;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.view_cell_layout,parent, false);
        return new MusicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MusicViewHolder holder, int position) {
        final MusicData music = musics.get(position);

        Picasso.get().load(music.picPath).into(holder.avatar);
        holder.artist.setText(String.format(holder.itemView.getResources().getString(R.string.name_field), music.firstName, music.lastName));
        holder.title.setText(music.title);
        holder.favourite.setImageResource(music.fav != 0 ? R.drawable.favorite_24dp : R.drawable.favorite_border_24dp);
    }

    public void setMusics(List<MusicData> musics) {
        this.musics = musics;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(musics != null) {
            return musics.size();
        }
        return 0;
    }
}