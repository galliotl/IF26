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
import utt.if26.bardcamp.model.MusicData;


public class Repository {
    private MusicDAO musicDAO;
    private FavDAO favDAO;


    public Repository(Application application) {
        AppDB db = AppDB.getDatabase(application);
        favDAO = db.favDAO();
        musicDAO = db.musicDAO();
    }

    public void insertMusic (Music music) {
        new insertMusicAsyncTask(musicDAO).execute(music);
    }

    public void deleteMusic(int mid) {
        new deleteMusicAsyncTask(musicDAO).execute(mid);
    }

    public LiveData<List<MusicData>> getFaved(String username) {
        return favDAO.getFavedMusics(username);
    }

    public LiveData<List<MusicData>> getFeed(String username) {
        LiveData<List<MusicData>> toReturn = null;
        try {
            toReturn = new getFeedAsyncTask(musicDAO).execute(username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    public void insertFav(int mid, String username) {
        Favourite fav = new Favourite(mid, username);
        new insertFavAsyncTask(favDAO).execute(fav);
    }
    public void deleteFav (int mid, String username) {
        new deleteFavAsyncTask(favDAO, username, mid).execute();
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

    private static class getFeedAsyncTask extends AsyncTask<String, Void, LiveData<List<MusicData>>>{
        MusicDAO musicDAO;

        public getFeedAsyncTask(MusicDAO musicDAO) {
            this.musicDAO = musicDAO;
        }

        @Override
        protected LiveData<List<MusicData>> doInBackground(String... strings) {
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

    private static class deleteMusicAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MusicDAO mAsyncTaskDao;

        deleteMusicAsyncTask(MusicDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... strings) {
            mAsyncTaskDao.delete(strings[0]);
            return null;
        }
    }

}
