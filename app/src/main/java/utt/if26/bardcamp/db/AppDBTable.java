package utt.if26.bardcamp.db;

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
        public static final String _ID = "mid";
    }

    public static class Favourite implements BaseColumns {
        public static final String TABLE_NAME = "favourite";

        public static final String COLUMN_USER = "user";
        public static final String COLUMN_MUSIC = "music";
        public static final String _ID = "fid";
    }

    /**
     * In our app Artist = User
     */
    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_PIC_PATH = "picPath";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FIRSTNAME = "firstName";
        public static final String COLUMN_PSW = "psw";
        public static final String _ID = "username";
    }
}
