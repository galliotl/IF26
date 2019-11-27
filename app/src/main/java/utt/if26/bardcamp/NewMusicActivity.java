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
                if(!titleTV.getText().toString().equals("") && filePath != null) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("title", titleTV.getText().toString());
                    returnIntent.putExtra("filePath", filePath);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "One of the fields isn't filled", Toast.LENGTH_SHORT).show();
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
                Intent getIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                getIntent.setType("audio/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,"audio/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Music");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                filePath = data.getData().toString();
            }
        }
    }
}
