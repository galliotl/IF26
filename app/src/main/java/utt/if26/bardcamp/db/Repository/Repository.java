package utt.if26.bardcamp.db.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import utt.if26.bardcamp.db.AppDB;
import utt.if26.bardcamp.db.Dao.FavDAO;
import utt.if26.bardcamp.db.Dao.MusicDAO;
import utt.if26.bardcamp.db.Entity.Favourite;
import utt.if26.bardcamp.db.Entity.Music;
import utt.if26.bardcamp.db.dataSource.LoginDataSource;
import utt.if26.bardcamp.model.MusicUI;


public class Repository {
    private MusicDAO musicDAO;
    private FavDAO favDAO;

    private LoginDataSource loginDataSource;
    private LiveData<String> userId;

    public Repository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        favDAO = db.favDAO();
        musicDAO = db.musicDAO();
        loginDataSource = new LoginDataSource(application.getApplicationContext()); // TODO: see if it is really needed or mabe make an observer in each activities
        userId = loginDataSource.getUserId();
    }

    public void insertMusic (Music music) {new insertMusicAsyncTask(musicDAO).execute(music);}

    public LiveData<List<MusicUI>> getFaved() {
        return favDAO.getFavedMusics(userId.getValue());
    }

    public LiveData<List<MusicUI>> getFeed() {
        LiveData<List<MusicUI>> toReturn = null;
        try {
            toReturn = new getFeedAsyncTask(musicDAO).execute(userId.getValue()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    public void insertFav (Favourite fav) {new insertFavAsyncTask(favDAO).execute(fav);}
    public void insertFav(int mid) {
        String uid = userId.getValue();
        if(uid != null) {
            Favourite fav = new Favourite(mid, uid);
            new insertFavAsyncTask(favDAO).execute(fav);
        }
    }
    public void deleteFav (int mid) {
        String uid = userId.getValue();
        if(uid != null) new deleteFavAsyncTask(favDAO, uid, mid).execute();
    }

    private static class insertMusicAsyncTask extends AsyncTask<Music, Void, Void> {

        private MusicDAO mAsyncTaskDao;

        insertMusicAsyncTask(MusicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Music... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class insertFavAsyncTask extends AsyncTask<Favourite, Void, Void> {

        private FavDAO mAsyncTaskDao;

        insertFavAsyncTask(FavDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favourite... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getFeedAsyncTask extends AsyncTask<String, Void, LiveData<List<MusicUI>>>{
        MusicDAO musicDAO;

        public getFeedAsyncTask(MusicDAO musicDAO) {
            this.musicDAO = musicDAO;
        }

        @Override
        protected LiveData<List<MusicUI>> doInBackground(String... strings) {
            if(strings[0] == null) return musicDAO.getFeed();
            return musicDAO.getFeed(strings[0]);
        }
    }

    private static class deleteFavAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavDAO mAsyncTaskDao;
        private int mid;
        private String uid;

        deleteFavAsyncTask(FavDAO dao, String uid, int mid) {
            this.mid = mid;
            this.uid = uid;
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.delete(mid, uid);
            return null;
        }
    }
}
