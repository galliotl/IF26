package utt.if26.bardcamp.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import utt.if26.bardcamp.db.Dao.FavDAO;
import utt.if26.bardcamp.db.Dao.MusicDAO;
import utt.if26.bardcamp.db.Dao.UserDAO;
import utt.if26.bardcamp.db.Entity.Favourite;
import utt.if26.bardcamp.db.Entity.Music;
import utt.if26.bardcamp.db.Entity.User;

@Database(entities = {User.class, Music.class, Favourite.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static final String DB_NAME = "BardCampDatabase";
    private static volatile AppDB INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
     };

    public abstract UserDAO userDAO();
    public abstract MusicDAO musicDAO();
    public abstract FavDAO favDAO();

    public static AppDB getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, DB_NAME)/*.addCallback(sRoomDatabaseCallback)*/.build();
                }
            }
        }
        return INSTANCE;
    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MusicDAO musicDAO;
        private final FavDAO favDAO;
        private final UserDAO userDAO;

        PopulateDbAsync(AppDB db) {
            favDAO = db.favDAO();
            musicDAO = db.musicDAO();
            userDAO = db.userDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            favDAO.deleteAll();
            musicDAO.deleteAll();
            userDAO.deleteAll();
            String username = "lucas.galliot@utt.fr";
            String username2 = "trevor.wallace@gmail.com";
            userDAO.insert(new User("lucas.galliot@utt.fr", "Lucas", "Galliot", "password"));
            userDAO.insert(new User("trevor.wallace@gmail.com", "Trevor", "Wallace", "password"));

            Long musicId = musicDAO.insert(new Music("title", username2, "path/to/music"));
            musicDAO.insert(new Music("title1", username, "path/to/music"));
            musicDAO.insert(new Music("title2", username, "path/to/music"));
            musicDAO.insert(new Music("title3", username2, "path/to/music"));
            musicDAO.insert(new Music("title4", username, "path/to/music"));

            favDAO.insert(new Favourite(musicId.intValue(), username2));
            return null;
        }
    }
}
