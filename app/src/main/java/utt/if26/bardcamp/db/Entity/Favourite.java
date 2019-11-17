package utt.if26.bardcamp.db.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import utt.if26.bardcamp.db.AppDBTable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = AppDBTable.Favourite.TABLE_NAME,
        foreignKeys = {
                @ForeignKey(
                        onDelete = CASCADE,
                        entity = User.class,
                        parentColumns = AppDBTable.User._ID,
                        childColumns = AppDBTable.Favourite.COLUMN_USER),
                @ForeignKey(
                        entity = Music.class,
                        parentColumns = AppDBTable.Music._ID,
                        childColumns = AppDBTable.Favourite.COLUMN_MUSIC),
        },
        indices = {@Index(AppDBTable.Favourite.COLUMN_MUSIC), @Index(AppDBTable.Favourite.COLUMN_USER)})
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = AppDBTable.Favourite._ID)
    public int id;

    @ColumnInfo(name = AppDBTable.Favourite.COLUMN_MUSIC)
    public int music;

    @ColumnInfo(name = AppDBTable.Favourite.COLUMN_USER)
    public String user;

    @Ignore
    public Favourite(int music, String user) {
        this.music = music;
        this.user = user;
    }

    public Favourite(){}
}
