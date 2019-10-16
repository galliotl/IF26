package utt.if26.bardcamp.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.models.Music;
import utt.if26.bardcamp.views.MusicListViewHolder;

import static android.content.ContentValues.TAG;

public class MusicAdapter extends ArrayAdapter<Music> {

    public MusicAdapter(Context context, List<Music> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_cell_layout,parent, false);
        }

        MusicListViewHolder viewHolder = (MusicListViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MusicListViewHolder();
            viewHolder.artist = convertView.findViewById(R.id.music_artist);
            viewHolder.title = convertView.findViewById(R.id.music_title);
            viewHolder.avatar = convertView.findViewById(R.id.music_avatar);
            viewHolder.favourite = convertView.findViewById(R.id.music_favourite);
            convertView.setTag(viewHolder);
        }

        final Music music = getItem(position);

        viewHolder.artist.setText(music.getArtistName());
        viewHolder.title.setText(music.getTitle());
        if(music.isFavourite()) viewHolder.favourite.setColorFilter(R.color.colorAccent);
        Picasso.get().load(music.getPicPath()).into(viewHolder.avatar);

        final MusicListViewHolder finalViewHolder = viewHolder;
        viewHolder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.setFavourite(!music.isFavourite());
                Log.d(TAG, "CLICKED ON THE BUTTON" + music.isFavourite());
                if(music.isFavourite()) {
                    finalViewHolder.favourite.setColorFilter(R.color.colorAccent);
                } else {
                    finalViewHolder.favourite.setColorFilter(R.color.colorGrey);
                }
            }
        });

        return convertView;
    }
}
