package utt.if26.bardcamp;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.SQLData;
import java.util.ArrayList;

import utt.if26.bardcamp.fragments.AccountFragment;
import utt.if26.bardcamp.fragments.FeedFragment;
import utt.if26.bardcamp.fragments.MusicFragment;
import utt.if26.bardcamp.models.Music;
import utt.if26.bardcamp.services.MusicService;

import utt.if26.bardcamp.bdd.test;

//import utt.if26.bardcamp.bdd.SQL;



public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;





    private SQLiteDatabase db;
   // private SQL test;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureBottomView();
        loadFragment(new FeedFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();

        new test();
        //test.onCreate(db);

        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(new ArrayList<Music>());
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shuffle:
                //shuffle
                break;
            case R.id.action_end:
                stopService(playIntent);
                musicSrv=null;
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void musicPicked(View view){
        musicSrv.setMusic(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
    }

    private void configureBottomView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_feed:
                        fragment = new FeedFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.action_music:
                        fragment = new MusicFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.action_account:
                        fragment = new AccountFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
