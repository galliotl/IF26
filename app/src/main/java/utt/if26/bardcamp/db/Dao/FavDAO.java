package utt.if26.bardcamp.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import utt.if26.bardcamp.db.AppDBTable;
import utt.if26.bardcamp.db.Entity.Favourite;
import utt.if26.bardcamp.model.MusicUI;

@Dao
public interface FavDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Favourite fav);

    @Query("DELETE FROM "+ AppDBTable.Favourite.TABLE_NAME)
    void deleteAll();

    @Query("SELECT m."+AppDBTable.Music._ID+" as "+AppDBTable.Music._ID
            + ", m."+AppDBTable.Music.COLUMN_TITLE+" as "+AppDBTable.Music.COLUMN_TITLE
            + ", m."+AppDBTable.Music.COLUMN_PIC_PATH+" as "+AppDBTable.Music.COLUMN_PIC_PATH
            + ", m."+AppDBTable.Music.COLUMN_PATH+" as "+AppDBTable.Music.COLUMN_PATH
            + ", u."+AppDBTable.User.COLUMN_FIRSTNAME+" as "+AppDBTable.User.COLUMN_FIRSTNAME
            + ", u."+AppDBTable.User.COLUMN_NAME+" as "+AppDBTable.User.COLUMN_NAME
            + ", f."+AppDBTable.Favourite._ID+" as "+AppDBTable.Favourite._ID
            + " FROM (SELECT * FROM "+AppDBTable.Favourite.TABLE_NAME+" WHERE "+AppDBTable.Favourite.COLUMN_USER+"=:userId) f"
            + " LEFT JOIN "+AppDBTable.Music.TABLE_NAME+" m ON m."+AppDBTable.Music._ID+" = f."+AppDBTable.Favourite.COLUMN_MUSIC
            + " LEFT JOIN "+AppDBTable.User.TABLE_NAME+" u ON u."+AppDBTable.User._ID+" = m."+AppDBTable.Music.COLUMN_ARTIST)
    LiveData<List<MusicUI>> getFavedMusics(String userId);

    @Delete
    void delete(Favourite fav);

    @Query("DELETE FROM "+ AppDBTable.Favourite.TABLE_NAME+" WHERE "+AppDBTable.Favourite._ID+"=:fid")
    void delete(int fid);

    @Query("DELETE FROM "+ AppDBTable.Favourite.TABLE_NAME+" WHERE "+AppDBTable.Favourite.COLUMN_MUSIC+"=:mid AND "+AppDBTable.Favourite.COLUMN_USER+"=:uid")
    void delete(int mid, String uid);

}
