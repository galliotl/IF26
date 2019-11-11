package utt.if26.bardcamp.db.dataSource;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginDataSource {
    MutableLiveData<String> currentUserId = new MutableLiveData<>();
    SharedPreferences sharedPreferences;

    private final String PREFERENCE_NAME = "Bardcamp";
    private final int PRIVATE_MODE = 0;

    public LoginDataSource(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        setCurrentUserId(sharedPreferences.getString("userId", null));
    }

    public LiveData<String> getUserId(){
        return currentUserId;
    }

    public void setCurrentUserId(String userId){
        currentUserId.setValue(userId);
    }

    public boolean isLoggedIn() { return currentUserId.getValue() != null; }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        currentUserId.setValue(null);
        editor.apply();
    }

    public void login(String userId){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", userId);
        currentUserId.setValue(null);
        editor.apply();
    }
}
