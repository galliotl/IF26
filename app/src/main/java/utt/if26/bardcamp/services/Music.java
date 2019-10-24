package com.example.myfmusic;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {



    MediaPlayer media;

    public Music() {
    }

    public void onCreate(Context context){
        media = MediaPlayer.create(context, R.raw.main_title);
        media.start();
    }

    protected void onPause() {
        media.pause();
        //media.release();

    }


    protected void onResume() {
        media.start();
    }
}
