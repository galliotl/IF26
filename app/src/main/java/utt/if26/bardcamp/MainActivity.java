package utt.if26.bardcamp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import utt.if26.bardcamp.db.Entity.User;
import utt.if26.bardcamp.fragments.AccountFragment;
import utt.if26.bardcamp.fragments.FeedFragment;
import utt.if26.bardcamp.fragments.MusicFragment;
import utt.if26.bardcamp.service.MusicService;
import utt.if26.bardcamp.util.LoginResult;
import utt.if26.bardcamp.viewModel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    User user;
    LiveData<LoginResult> loginResult;
    LoginViewModel loginViewModel;

    private MusicService musicService;
    private boolean bound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.LocalBinder) service).getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindToService();
        configureLogin();
        configureBottomView();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    public MusicService getMusicService() {
        if(!bound) bindToService();
        return musicService;
    }

    public void bindToService() {
        Intent playerIntent = new Intent(this, MusicService.class);
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void configureBottomView(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(user != null) {
                    Fragment fragment;
                    Bundle bundle;
                    switch (item.getItemId()) {
                        case R.id.action_feed:
                            bundle = new Bundle();
                            bundle.putString("userId", user.userName);
                            fragment = new FeedFragment();
                            fragment.setArguments(bundle);
                            loadFragment(fragment);
                            return true;
                        case R.id.action_music:
                            fragment = new MusicFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.action_account:
                            bundle = new Bundle();
                            bundle.putString("userId", user.userName);
                            fragment = new AccountFragment();
                            fragment.setArguments(bundle);
                            loadFragment(fragment);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    private void configureLogin() {
        loginViewModel = new LoginViewModel(this.getApplication());
        loginResult = loginViewModel.getLoginResult();

        loginResult.observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                if(loginResult.getSuccess() != null) {
                    user = loginResult.getSuccess();
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", user.userName);
                    Fragment feed = new FeedFragment();
                    feed.setArguments(bundle);
                    loadFragment(feed);
                } else {
                    user = null;
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    Toast.makeText(getApplicationContext(), "User was disconnected", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void loadFragment(Fragment fragment) {
         //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
