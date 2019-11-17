package utt.if26.bardcamp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.text.BoringLayout;
import android.util.Patterns;

import java.io.IOException;

import utt.if26.bardcamp.db.Entity.User;
import utt.if26.bardcamp.db.Repository.LoginRepository;
import utt.if26.bardcamp.util.Result;
import utt.if26.bardcamp.R;
import utt.if26.bardcamp.ui.login.LoginFormState;
import utt.if26.bardcamp.util.LoginResult;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    private LoginRepository repository;

    public LoginViewModel(Application application) {
        this.repository = new LoginRepository(application);
        loginResult.setValue(new LoginResult(repository.getLoggedInUser()));
    }

    public User getUser(String username) {
        return repository.getUser(username);
    }

    public Boolean deleteAccount(String username) {
        return repository.deleteUser(username);
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = repository.login(username, password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            loginResult.setValue(new LoginResult(data));
        } else {
            Exception e = ((Result.Error) result).getError();
            if(e instanceof IOException) {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            } else {
                loginResult.setValue(new LoginResult(R.string.login_not_known));
            }
        }
    }

    public void signup(User user){
        Result<User> result = repository.signup(user);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            loginResult.setValue(new LoginResult(data));
        } else {
            Exception e = ((Result.Error) result).getError();
            loginResult.setValue(new LoginResult(R.string.signup_fail));
        }
    }

    public void logout() {
        repository.logout();
        loginResult.setValue(new LoginResult(R.string.signup_fail));
    }

    public boolean isLoggedIn() {
        return repository.isLoggedIn();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
