package utt.if26.bardcamp.Music;

import android.content.Context;
import android.media.MediaPlayer;
//import android.net.Uri;
import android.net.Uri;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utt.if26.bardcamp.R;


public class Music implements MediaPlayer.OnCompletionListener {



    public MediaPlayer media;
    LinkedList<Integer> playlist;
    Context cont;
    Iterator iterator;
    int avance;
    public Music() {}

    public void onCreate(Context context){

        playlist = new LinkedList();
        playlist.add(R.raw.knife_game_song);
        playlist.add(R.raw.main_title);
        playlist.add(R.raw.aviators);
        playlist.add(R.raw.mlp);
        playlist.add(R.raw.one);
        //iterator = playlist.iterator();


        cont = context;

        avance = 0;
        media = MediaPlayer.create(context, playlist.get(0));
        media.start();
        media.pause();

        media.setOnCompletionListener(this);
    }

    public void onPause() {
        Log.d("STATE", "### onPause Music");
        media.pause();
        //media.release();

    }


    public void onResume() {
        Log.d("STATE", "### onResume Music");
        media.start();
    }

    public void onStop() {

        media.stop();
        media.release();
    }

    /**
     * joue la musique suivante ou précédente
     * @param next
     * @param auto
     */
    public void playNext( boolean next,  boolean auto){
        Log.d("STATE", "### playNext Music");
        boolean stopped = media.isPlaying();
        media.pause();
        /*if(next) {
            avance++;
            if (avance == playlist.size()){
                avance = 0 ;
            }
        }else{
            avance--;
            if (avance <0){
                avance=playlist.size() -1;
            }

        }*/
        if(next) {
            avance++;
        }else{
            avance+=playlist.size() - 1;
        }
        avance%=playlist.size();

        onStop();
        media = MediaPlayer.create(cont, playlist.get(avance));

        if (stopped||auto) {
            media.start();
        }

        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playNext(true, true);
            }
        });


        cont.getResources().getResourceEntryName(R.raw.knife_game_song);

    }

    /**
     * se lance quand une musique est finie
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {

        Log.d("STATE", "### Complete Music");
        playNext(true, true);
    }
}
