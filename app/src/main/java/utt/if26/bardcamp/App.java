package utt.if26.bardcamp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    // App constants
    public static final String CHANNEL_ID = "notificationChannel";
    public static final String ACTION_PLAY_MUSIC = "utt.if26.bardcamp.action.PLAY_PAUSE_MUSIC";
    public static final String ACTION_PAUSE_MUSIC = "utt.if26.bardcamp.action.PAUSE_MUSIC";
    public static final String ACTION_PREVIOUS_MUSIC = "utt.if26.bardcamp.action.PREVIOUS_MUSIC";
    public static final String ACTION_NEXT_MUSIC = "utt.if26.bardcamp.action.NEXT_MUSIC";
    public static final String ACTION_FOREGROUND_START = "utt.if26.bardcamp.action.START_FOREGROUND";
    public static final String ACTION_FOREGROUND_STOP = "utt.if26.bardcamp.action.STOP_FOREGROUND";
    public static final int FOREGROUND_SERVICE = 101;


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "exemple channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
