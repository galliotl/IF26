package utt.if26.bardcamp.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import utt.if26.bardcamp.db.AppDBTable;
import utt.if26.bardcamp.db.Entity.User;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(User... users);

    @Query("DELETE FROM "+ AppDBTable.Favourite.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM "+ AppDBTable.User.TABLE_NAME+" WHERE "+ AppDBTable.User._ID+"=:userId")
    User getUser(String userId);

    @Query("SELECT * FROM "+AppDBTable.User.TABLE_NAME)
    LiveData<List<User>> getAllUser();
}
