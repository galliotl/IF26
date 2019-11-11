package utt.if26.bardcamp.db.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import utt.if26.bardcamp.db.AppDBTable;


@Entity(tableName = AppDBTable.Music.TABLE_NAME,
        indices = {@Index(AppDBTable.Music.COLUMN_ARTIST)}
        )
public class Music {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDBTable.Music._ID)
    public int id;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_TITLE)
    public String title;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_PATH)
    public String path;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_PIC_PATH)
    public String picPath;

    @ColumnInfo(name = AppDBTable.Music.COLUMN_ARTIST)
    public String artist;

    @Ignore
    public Music(String title, String artist, String path, String picPath) {
        this.title = title;
        this.path = path;
        this.picPath = picPath;
        this.artist = artist;
    }

    @Ignore
    public Music(String title, String artist, String path) {
        this.title = title;
        this.path = path;
        this.picPath = "http://www.clker.com/cliparts/n/T/x/Z/f/L/music-note-th.png";
        this.artist = artist;
    }

    public Music(){}
}
