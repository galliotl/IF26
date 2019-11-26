package utt.if26.bardcamp.service;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import utt.if26.bardcamp.model.MusicData;
import utt.if26.bardcamp.util.DurationConverter;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private final IBinder mBinder = new LocalBinder();
    private List<MusicData> playlist;
    private int currentPositionInPlaylist;
    private MediaPlayer mediaPlayer;
    private int resumePosition;
    private MutableLiveData<MusicData> currentMusic = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    private DurationConverter dc = new DurationConverter();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {

        public MusicService getService(){
            return MusicService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getCurrentMusicTimer() {
        return mediaPlayer.getCurrentPosition();
    }

    public LiveData<MusicData> getCurrentMusicLiveData() {
        return currentMusic;
    }

    public LiveData<Boolean> isPlayingLiveData() {
        return isPlaying;
    }

    public int getMusicDuration() {
        return mediaPlayer.getDuration();
    }

    /**
     * This plays the currently prepared song
     */
    private void playMedia() {
        if (!mediaPlayer.isPlaying()) {
            isPlaying.setValue(true);
            currentMusic.setValue(playlist.get(currentPositionInPlaylist));
            mediaPlayer.start();
        }
    }

    public void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            isPlaying.setValue(false);
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        }
    }

    public void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            isPlaying.setValue(true);
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }

    /**
     * prepare the next music if exist or stop
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        playNext();
    }

    public void playNext() {
        if(playlist == null) return;
        if(currentPositionInPlaylist < playlist.size() - 1) {
            currentPositionInPlaylist ++;
            playFromCurrentPosition();
        }
        // we go back to the beginning of the playlist
        else {
            currentPositionInPlaylist = 0;
            playFromCurrentPosition();
        }
    }

    public void playPrevious() {
        if(playlist == null) return;
        if(currentPositionInPlaylist > 0) {
            currentPositionInPlaylist --;
            playFromCurrentPosition();
        }
        // we go back to the beginning of the playlist
        else {
            currentPositionInPlaylist = 0;
            playFromCurrentPosition();
        }
    }

    /**
     * Invoked when the media source is ready for playback
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
    }

    /**
     * resets the position of the cursor and change the titles to play
     * @param newPlaylist
     */
    public void setPlaylist(List<MusicData> newPlaylist) {
        playlist = newPlaylist;
    }

    /**
     * The main command that can be called from the outside to start a new project
     * @param newPlaylist
     */
    public void playFromNewPlaylist(List<MusicData> newPlaylist){
        setPlaylist(newPlaylist);
        currentPositionInPlaylist = 0;
        playFromCurrentPosition();
    }

    private void playFromCurrentPosition() {
        if(mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, Uri.parse(playlist.get(currentPositionInPlaylist).path));
        mediaPlayer.setOnCompletionListener(this);
        playMedia();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            stopSelf();
        }
    }
    
    public void seekTo(int positionInPercentage) {
        int milliseconds = dc.percentageToMillis(positionInPercentage, mediaPlayer.getDuration());
        if(mediaPlayer != null) mediaPlayer.seekTo(milliseconds);
    }
}

