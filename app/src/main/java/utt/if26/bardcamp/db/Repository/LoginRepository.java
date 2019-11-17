package utt.if26.bardcamp.db.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import utt.if26.bardcamp.db.AppDB;
import utt.if26.bardcamp.db.Dao.FavDAO;
import utt.if26.bardcamp.db.Dao.MusicDAO;
import utt.if26.bardcamp.db.Dao.UserDAO;
import utt.if26.bardcamp.db.dataSource.LoginDataSource;
import utt.if26.bardcamp.db.Entity.User;
import utt.if26.bardcamp.util.Result;

public class LoginRepository {

    private UserDAO userDAO;
    private MusicDAO musicDAO;
    private FavDAO favDAO;
    private LoginDataSource loginDataSource;
    private LiveData<String> userId;

    public LoginRepository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        userDAO = db.userDAO();
        favDAO = db.favDAO();
        musicDAO = db.musicDAO();
        loginDataSource = new LoginDataSource(application.getApplicationContext());
        userId = loginDataSource.getUserId();
    }

    public User getLoggedInUser() {
        User user = null;
        try {
            user = new getUserAsyncTask(userDAO).execute(userId.getValue()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUser(String uid){
        if(uid == null) return null;
        User user = null;
        try {
            user = new getUserAsyncTask(userDAO).execute(uid).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isLoggedIn() {
        return loginDataSource.isLoggedIn();
    }

    public void logout() {
        loginDataSource.logout();
    }

    public Result<User> login(String username, String password) {
        User user;
        try {
            user = new getUserAsyncTask(userDAO).execute(username).get();
        } catch (Exception e) {
            return new Result.Error(new Exception("an error has occurred"));
        }
        if(user == null) {
            return new Result.Error(new Exception("User not known"));
        } else {
            if(password.equals(user.psw)) {
                loginDataSource.login(username);
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new IOException("User or password wrong"));
            }
        }
    }

    public Result<User> signup(User user){
        try {
            if(new insertUserAsyncTask(userDAO).execute(user).get()) {
                loginDataSource.login(user.userName);
                return new Result.Success<>(user);
            }
            return new Result.Error(new Exception("Username already exists"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    public Boolean deleteUser(String username) {
        try {
            if(new deleteUserAsyncTask(userDAO, favDAO, musicDAO).execute(username).get()) {
                logout();
                return true;
            }
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class insertUserAsyncTask extends AsyncTask<User, Void, Boolean> {

        private UserDAO mAsyncTaskDao;

        insertUserAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final User... params) {
            try {
                mAsyncTaskDao.insert(params[0]);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private static class deleteUserAsyncTask extends AsyncTask<String, Void, Boolean> {

        private UserDAO mAsyncTaskDao;
        private FavDAO mAsyncTaskDaoFav;
        private MusicDAO mAsyncTaskDaoMusic;

        deleteUserAsyncTask(UserDAO dao, FavDAO favDAO, MusicDAO musicDAO) {
            mAsyncTaskDao = dao;
            mAsyncTaskDaoMusic = musicDAO;
            mAsyncTaskDaoFav = favDAO;
        }

        @Override
        protected Boolean doInBackground(final String... params) {
            try {
                mAsyncTaskDao.delete(params[0]);
                mAsyncTaskDaoFav.deleteWithUser(params[0]);
                mAsyncTaskDaoMusic.deleteWithUser(params[0]);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    private static class getUserAsyncTask extends AsyncTask<String, Void, User> {
        UserDAO userDAO;

        getUserAsyncTask(UserDAO dao) {
            userDAO = dao;
        }

        @Override
        protected User doInBackground(String... strings) {
            return userDAO.getUser(strings[0]);
        }
    }
}
