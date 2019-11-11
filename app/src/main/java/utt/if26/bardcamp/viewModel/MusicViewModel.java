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
    public LiveData<List<MusicUI>> getFeed() {return repository.getFeed();}
    public LiveData<List<MusicUI>> getFaved() {return repository.getFaved();}

    public void insertFav(int mid) {repository.insertFav(mid);}
    public void deleteFav(int mid) {repository.deleteFav(mid);}
}
