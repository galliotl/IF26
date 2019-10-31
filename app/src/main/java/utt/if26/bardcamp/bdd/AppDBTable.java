package utt.if26.bardcamp.bdd;

import android.provider.BaseColumns;

/**
 * This class defines all of our tables
 */
public class AppDBTable {

    private AppDBTable(){}

    public static class Music implements BaseColumns {
        public static final String TABLE_NAME = "music";

        public static final String COLUMN_ARTIST = "artist";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_PIC_PATH = "picPath";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FOREIGN KEY(" + COLUMN_ARTIST + ") REFERENCES " +
                User.TABLE_NAME + "(" + User._ID + ") " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_PATH + " TEXT, " +
                COLUMN_PIC_PATH + " TEXT" + ")";
    }

    public static class Favourite implements BaseColumns {
        public static final String TABLE_NAME = "favourite";

        public static final String COLUMN_USER = "user";
        public static final String COLUMN_MUSIC = "music";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FOREIGN KEY(" + COLUMN_USER + ") REFERENCES " +
                User.TABLE_NAME + "(" + User._ID + ") " +
                "FOREIGN KEY(" + COLUMN_MUSIC + ") REFERENCES " +
                Music.TABLE_NAME + "(" + Music._ID + ") " + ")";
    }

    /**
     * In our app Artist = User
     */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_PIC_PATH = "picPath";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FIRSTNAME = "firstName";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PIC_PATH + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_FIRSTNAME + " TEXT" + ")";
    }
}
