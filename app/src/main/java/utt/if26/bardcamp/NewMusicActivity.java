package utt.if26.bardcamp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewMusicActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 1;
    String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_music_activity);
        final TextView titleTV = findViewById(R.id.music_name);
        FloatingActionButton fabDone = findViewById(R.id.fab_done_music);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleTV.getText() != null && filePath != null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("title", titleTV.getText().toString());
                    returnIntent.putExtra("filePath", filePath);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });

        ImageButton btnClose = findViewById(R.id.btn_close_new_music);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

        Button btnMusic = findViewById(R.id.music_file);
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setType("audio/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData().getPath();
                Toast.makeText(getApplicationContext(), filePath, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
