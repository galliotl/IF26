package utt.if26.bardcamp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import utt.if26.bardcamp.viewModel.LoginViewModel;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        LoginViewModel loginViewModel = new LoginViewModel(this.getApplication());
        Intent activityIntent;
        if (loginViewModel.isLoggedIn()) {
            activityIntent = new Intent(this, MainActivity.class);
        } else {
            activityIntent = new Intent(this, LoginActivity.class);
        }
        startActivity(activityIntent);
        finish();
    }
}
