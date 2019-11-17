package utt.if26.bardcamp.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import utt.if26.bardcamp.db.AppDBTable;
import utt.if26.bardcamp.db.Entity.Music;
import utt.if26.bardcamp.model.MusicUI;

@Dao
public interface MusicDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Music music);

    @Query("DELETE FROM "+ AppDBTable.Music.TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM "+ AppDBTable.Music.TABLE_NAME+" WHERE "+AppDBTable.Music.COLUMN_ARTIST+"=:username")
    void deleteWithUser(String username);

    /**
     * This functions returns all the musicUI in the database, with the one a given user has liked
     * @return LiveData List MusicUI
     */
    @Query("SELECT"
            // Projection
            + " m."+AppDBTable.Music._ID
            + ", m."+AppDBTable.Music.COLUMN_TITLE
            + ", m."+AppDBTable.Music.COLUMN_PIC_PATH
            + ", m."+AppDBTable.Music.COLUMN_PATH
            + ", u."+AppDBTable.User.COLUMN_FIRSTNAME
            + ", u."+AppDBTable.User.COLUMN_NAME
            + ", f."+AppDBTable.Favourite._ID
            // Table name
            + " FROM "+AppDBTable.Music.TABLE_NAME+" m"
            // Left join
            + " JOIN "+AppDBTable.User.TABLE_NAME+" u"
            + " ON m."+AppDBTable.Music.COLUMN_ARTIST+"=u." + AppDBTable.User._ID
            + " LEFT OUTER JOIN"
            + " (SELECT * FROM "+AppDBTable.Favourite.TABLE_NAME
            + " WHERE "+AppDBTable.Favourite.COLUMN_USER+"=:userId) f"
            + " ON f."+AppDBTable.Favourite.COLUMN_MUSIC+"=m."+AppDBTable.Music._ID)
    LiveData<List<MusicUI>> getFeed(String userId);

    /**
     * This functions returns all the musicUI in the database, with no one liked
     * @return LiveData List MusicUI
     */
    @Query("SELECT"
            // Projection
            + " m."+AppDBTable.Music._ID
            + ", m."+AppDBTable.Music.COLUMN_TITLE
            + ", m."+AppDBTable.Music.COLUMN_PIC_PATH
            + ", m."+AppDBTable.Music.COLUMN_PATH
            + ", u."+AppDBTable.User.COLUMN_FIRSTNAME
            + ", u."+AppDBTable.User.COLUMN_NAME
            + ", f."+AppDBTable.Favourite._ID
            // Table name
            + " FROM "+AppDBTable.Music.TABLE_NAME+" m"
            // Left join
            + " JOIN "+AppDBTable.User.TABLE_NAME+" u"
            + " ON m."+AppDBTable.Music.COLUMN_ARTIST+"=u." + AppDBTable.User._ID
            + " LEFT OUTER JOIN"
            + " (SELECT * FROM "+AppDBTable.Favourite.TABLE_NAME
            + " WHERE "+AppDBTable.Favourite.COLUMN_USER+"=\"fakeid\") f"
            + " ON f."+AppDBTable.Favourite.COLUMN_MUSIC+"=m."+AppDBTable.Music._ID)
    LiveData<List<MusicUI>> getFeed();
}