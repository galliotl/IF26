package utt.if26.bardcamp.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import utt.if26.bardcamp.db.AppDBTable;

@Entity(tableName = AppDBTable.User.TABLE_NAME)
public class User {
    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = AppDBTable.User._ID)
    public String userName;

    @ColumnInfo(name = AppDBTable.User.COLUMN_FIRSTNAME)
    public String firstName;

    @ColumnInfo(name = AppDBTable.User.COLUMN_NAME)
    public String lastName;

    @ColumnInfo(name = AppDBTable.User.COLUMN_PSW)
    public String psw;

    @ColumnInfo(name = AppDBTable.User.COLUMN_PIC_PATH)
    public String picPath;

    @Ignore
    public User(@NonNull String userName, String firstName, String lastName, String psw, String picPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.psw = psw;
        this.picPath = picPath;
        this.userName = userName;
    }

    @Ignore
    public User(@NonNull String userName, String firstName, String lastName, String psw) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.psw = psw;
        this.picPath = "http://www.clker.com/cliparts/n/T/x/Z/f/L/music-note-th.png";
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User "+this.userName+":\nfirst-name: "+this.firstName+",\nlast-name: "+this.lastName+",\npic-path: "+this.picPath+",\npassword: "+this.psw;
    }

    public User() {}
}
