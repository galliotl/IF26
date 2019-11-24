package utt.if26.bardcamp.Interface;

import android.view.View;

public interface MusicClickListener {
    void onClick(View v, int position);
    void onFavouriteClick(View v, int position);
    boolean onLongPressed(View v, int position);
}
