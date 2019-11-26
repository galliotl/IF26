package utt.if26.bardcamp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import utt.if26.bardcamp.model.MusicData;
import utt.if26.bardcamp.service.MusicService;

public class MusicServiceViewModel extends ViewModel {

    private MutableLiveData<MusicService.LocalBinder> binder = new MutableLiveData<>();


    public LiveData<MusicService.LocalBinder> getBinder() {
        return binder;
    }


    // Actions
    public void playNewPlaylist(List<MusicData> playlist) {
        MusicService.LocalBinder tempBinder = binder.getValue();
        if(tempBinder != null) {
            tempBinder.getService().playFromNewPlaylist(playlist);
        }
    }
}
