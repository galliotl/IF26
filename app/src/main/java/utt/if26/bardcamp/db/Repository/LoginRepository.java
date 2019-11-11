package utt.if26.bardcamp.db.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import utt.if26.bardcamp.db.AppDB;
import utt.if26.bardcamp.db.Dao.UserDAO;
import utt.if26.bardcamp.db.dataSource.LoginDataSource;
import utt.if26.bardcamp.db.Entity.User;
import utt.if26.bardcamp.util.Result;

public class LoginRepository {

    private UserDAO userDAO;

    private LoginDataSource loginDataSource;
    private LiveData<String> userId;

    public LoginRepository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        userDAO = db.userDAO();
        loginDataSource = new LoginDataSource(application.getApplicationContext());
        userId = loginDataSource.getUserId();
    }

    public User getLoggedInUser() {
        User user = null;
        try {
            user = new getUserAsyncTask(userDAO).execute(userId.getValue()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
        User user = null;
        try {
            user = new getUserAsyncTask(userDAO).execute(username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    private static class insertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDAO mAsyncTaskDao;

        insertUserAsyncTask(UserDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
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
