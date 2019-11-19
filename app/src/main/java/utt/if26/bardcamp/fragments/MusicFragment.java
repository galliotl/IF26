package utt.if26.bardcamp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import utt.if26.bardcamp.MainActivity;
import utt.if26.bardcamp.Music.Music;
import utt.if26.bardcamp.R;

/*A AJOUTER AU MAIN
    Static Music musique;

        musique = new Music();
        musique.onCreate(this);

REMPLACER DANS MusicFragment.java
music -> MainActivity.musique
 */

//getResources().getResourceEntryName(R.raw.knife_game_song)

public class MusicFragment extends Fragment implements View.OnClickListener{

    Music music;
    TextView timer;
    SeekBar advance;
    boolean musicIsPlaying;
    public MusicFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.music_fragment, container, false);

        TextView artistSongText = rootView.findViewById(R.id.artist_song_text);

        ImageButton playPause = rootView.findViewById(R.id.play_pause_btn);

        if (MainActivity.musique.media.isPlaying()){
            ((ImageButton) rootView.findViewById(R.id.play_pause_btn)).setImageResource(R.drawable.pause_72dp);
        }
        else{
            ((ImageButton) rootView.findViewById(R.id.play_pause_btn)).setImageResource(R.drawable.play_72dp);

        }

        advance = rootView.findViewById(R.id.advance);
        timer = rootView.findViewById(R.id.timer);
        ImageButton buton = rootView.findViewById(R.id.btn_next);
        ImageButton butoff = rootView.findViewById(R.id.btn_previous);
        ImageButton butonoff = rootView.findViewById(R.id.play_pause_btn);
        advance = rootView.findViewById(R.id.advance);

        //music = new Music();


        timer.post(mUpdateTime);

        buton.setOnClickListener(this);
        butoff.setOnClickListener(this);
        butonoff.setOnClickListener(this);
        advance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //textview.setText(seekBar.getProgress()*music.media.getDuration()/100+" "+music.media.getCurrentPosition()+"/"+music.media.getDuration());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(MainActivity.musique.media.isPlaying()){
                    musicIsPlaying = true;
                }
                MainActivity.musique.onPause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainActivity.musique.media.seekTo(seekBar.getProgress()*MainActivity.musique.media.getDuration()/100);
                if(musicIsPlaying){
                    MainActivity.musique.onResume();
                }
            }
        } );
        return rootView;
    }

    @Override
    public void onClick(View view) {

        Log.d("STATE", "### click");
        switch (view.getId()){
            case R.id.btn_next:
                MainActivity.musique.playNext(true, false);
                break;
            case R.id.btn_previous:
                MainActivity.musique.playNext(false, false);

                break;
            case R.id.play_pause_btn:
                Log.d("STATE", "### click butonoff");
                if (MainActivity.musique.media.isPlaying()){
                    MainActivity.musique.onPause();
                    ((ImageButton) view).setImageResource(R.drawable.play_72dp);

                }
                else{
                    MainActivity.musique.onResume();
                    ((ImageButton) view).setImageResource(R.drawable.pause_72dp);
                }

                break;
        }

        Log.d("STATE", "### finclick");
    }




    private Runnable mUpdateTime = new Runnable() {
        public void run() {
            int currentDuration, fullDuration;
            //if (MainActivity.musique.media.isPlaying()) {
            currentDuration = MainActivity.musique.media.getCurrentPosition();//getCurrentPosition();
            fullDuration = MainActivity.musique.media.getDuration();
            updatePlayer(currentDuration, fullDuration);
            advance.setMax(0);
            advance.setMax(100);
            advance.setProgress((int)(((float)currentDuration/fullDuration)*100));
            timer.postDelayed(this, 1000);
        }
    };

    private void updatePlayer(int currentDuration, int fullDuration){
        timer.setText("" + milliSecondsToTimer((long) currentDuration)+" / "+milliSecondsToTimer((long)fullDuration));
    }

    /**
     * Converti une duree de milliseconde en un type heure:mnute:seconde
     *
     * */
    public  String milliSecondsToTimer(long time) {

        int heure   = (int) time / (1000 * 60 * 60);
        int minute  = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
        int seconde = (int)((time % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        String finalString="";

        if (heure > 0) {
            finalString += heure + ":";
            if (minute < 10){
                finalString+="0";
            }
        }
        finalString+=minute + ":";

        if (seconde < 10) {
            finalString += "0" + seconde;
        } else {
            finalString += "" + seconde;
        }

        return finalString;

    }

}
