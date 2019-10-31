package utt.if26.bardcamp.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bardcamp_database";

    public AppDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(AppDBTable.User.CREATE_TABLE);
        sqLiteDatabase.execSQL(AppDBTable.Music.CREATE_TABLE);
        sqLiteDatabase.execSQL(AppDBTable.Favourite.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AppDBTable.User.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AppDBTable.Music.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AppDBTable.Favourite.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
