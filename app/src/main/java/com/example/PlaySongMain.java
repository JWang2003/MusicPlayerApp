package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;

public class PlaySongMain extends AppCompatActivity {

    int mp3Resource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Created oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_main);
        
        // Process the extra information from intent
        getIncomingIntent();

        // Go back to mainactivity
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(PlaySongMain.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getIncomingIntent(){
        System.out.println("incoming intents");
        if(getIntent().hasExtra("SongName") && getIntent().hasExtra("ArtistName") && getIntent().hasExtra("ImageResource") && getIntent().hasExtra("Mp3Resource")){
            System.out.println("Found");
            String songName = getIntent().getStringExtra("SongName");
            String artistName = getIntent().getStringExtra("ArtistName");
            int imageResource = getIntent().getIntExtra("ImageResource", 0);
            int mp3Resource = getIntent().getIntExtra("Mp3Resource", 0);
            setDisplay(songName, artistName, imageResource);
        }
    }
    private void setDisplay(String songNamed, String artistName, int imageResource){
        TextView songTitle = findViewById(R.id.songName);
        songTitle.setText(songNamed);
        TextView artistTitle = findViewById(R.id.songWriter);
        artistTitle.setText(artistName);
        ImageView image = findViewById(R.id.songImage);
        image.setImageResource(imageResource);
    }
}