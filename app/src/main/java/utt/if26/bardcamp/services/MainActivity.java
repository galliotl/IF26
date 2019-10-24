package com.example.myfmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer mediaPlayer;

    Music music= new Music();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("STATE", "### aaaa "+savedInstanceState);
        Log.d("STATE", "### create");
        setContentView(R.layout.activity_main);

        music = new Music();
        music.onCreate(getApplicationContext());

        Button buton = findViewById(R.id.buton);
        Button butoff = findViewById(R.id.butoff);
        Button butonoff = findViewById(R.id.butonoff);

        buton.setOnClickListener(this);
        butoff.setOnClickListener(this);
        butonoff.setOnClickListener(this);

        Log.d("STATE", "### fin create");
    }

    @Override
    protected void onPause() {

        Log.d("STATE", "### Pause");
        super.onPause();
        music.onPause();
        super.onResume();

        Log.d("STATE", "### finPause");
    }
    @Override
    protected void onResume(){

        Log.d("STATE", "### Resume");
        super.onResume();
        music.onResume();

        Log.d("STATE", "### finResume");
    }

    @Override
    public void onClick(View v) {

        Log.d("STATE", "### click");
        switch (v.getId()){
            case R.id.buton:

                Log.d("STATE", "### click 1");
                if (!music.media.isPlaying()){
                    onResume();
                }
                break;
            case R.id.butoff:

                Log.d("STATE", "### click 2");
                if (music.media.isPlaying()){
                    onPause();
                }
                break;
            case R.id.butonoff:
                Log.d("STATE", "### click 3");
                if (music.media.isPlaying()){
                    onPause();
                }
                else{
                    onResume();
                }

                break;
        }

        Log.d("STATE", "### finclick");
    }


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Log.d("STATE", "### aaaa "+savedInstanceState.toString());
        super.onCreate(savedInstanceState);
        Log.d("STATE", "### bbb "+savedInstanceState.toString());
        setContentView(R.layout.activity_main);
        testMusic thread = new testMusic(savedInstanceState, 143);
        thread.setContext(savedInstanceState);
        thread.start();
    }*/
}
