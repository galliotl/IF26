package utt.if26.bardcamp.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import utt.if26.bardcamp.model.MusicUI;
import utt.if26.bardcamp.db.Repository.Repository;

public class MusicViewModel extends ViewModel {
    Repository repository;

    public MusicViewModel(Application application) {
        repository = new Repository(application);
    }
    public LiveData<List<MusicUI>> getFeed(String username) {return repository.getFeed(username);}
    public LiveData<List<MusicUI>> getFaved(String username) {return repository.getFaved(username);}

    public void insertFav(int mid, String username) {repository.insertFav(mid, username);}
    public void deleteFav(int mid, String username) {repository.deleteFav(mid, username);}
}
