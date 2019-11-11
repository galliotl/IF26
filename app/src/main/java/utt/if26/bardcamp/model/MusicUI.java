package utt.if26.bardcamp.model;

import androidx.room.ColumnInfo;

import utt.if26.bardcamp.db.AppDBTable;

public class MusicUI {
    // Music elements
    @ColumnInfo(name = AppDBTable.Music._ID)
    public int id;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_TITLE)
    public String title;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_PIC_PATH)
    public String picPath;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_PATH)
    public String path;

    // Artist element
    @ColumnInfo(name = AppDBTable.User.COLUMN_FIRSTNAME)
    public String firstName;

    @ColumnInfo(name = AppDBTable.User.COLUMN_NAME)
    public String lastName;

    // Fav
    @ColumnInfo(name = AppDBTable.Favourite._ID, defaultValue = "0")
    public int fav;
}
