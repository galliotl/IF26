package utt.if26.bardcamp.db.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import utt.if26.bardcamp.db.AppDBTable;
import utt.if26.bardcamp.db.Entity.User;

@Dao
public interface UserDAO {
    @Insert()
    long insert(User user);

    @Query("DELETE FROM "+ AppDBTable.User.TABLE_NAME+" WHERE "+AppDBTable.User._ID+"=:username")
    void delete(String username);

    @Query("DELETE FROM "+ AppDBTable.User.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM "+ AppDBTable.User.TABLE_NAME+" WHERE "+ AppDBTable.User._ID+"=:userId")
    User getUser(String userId);
}
