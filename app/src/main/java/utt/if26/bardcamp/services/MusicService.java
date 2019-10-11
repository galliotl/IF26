package utt.if26.bardcamp.services;

import android.media.MediaPlayer;

import java.util.ArrayList;

public class MusicService {
    private static MusicService musicService = new MusicService();
    MediaPlayer mPlayer = new MediaPlayer();

    private ArrayList<String> playlist = new ArrayList<String>();

    private MusicService() {}

    public static MusicService getInstance() {
        return musicService;
    }

    public void play(String path) {
        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPlayer.start();
    }

    void resume() {
        try {
            mPlayer.start();
        } catch (Exception e) {
            System.out.println("ERREUR TA MERE LA PUTE");
        }
    }

    public void playPlaylist() {

    }

    public void pause() {
        mPlayer.pause();
    }
}
