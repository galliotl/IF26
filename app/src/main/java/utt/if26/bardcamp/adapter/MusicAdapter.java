package utt.if26.bardcamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import utt.if26.bardcamp.R;
import utt.if26.bardcamp.models.Music;
import utt.if26.bardcamp.views.MusicListViewHolder;

public class MusicAdapter extends ArrayAdapter<Music> {

    //tweets est la liste des models à afficher
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

        Music music = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.artist.setText(music.getArtistName());
        viewHolder.title.setText(music.getTitle());
        viewHolder.avatar.setImageURI(music.getPicPath());

        return convertView;
    }
}
