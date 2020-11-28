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

import java.util.ArrayList;

public class PlaySongMain extends AppCompatActivity {

    int mp3Resource;
    Song currentSong;
    ArrayList<Song> songs;
    int currentSongIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (getIntent().hasExtra("Playlist") && getIntent().hasExtra("Index")){
            songs = getIntent().getParcelableArrayListExtra("Playlist");
            currentSongIndex = getIntent().getIntExtra("Index", 0);
            currentSong = songs.get(currentSongIndex);
            System.out.println(currentSongIndex);

            String songName = currentSong.songName;
            String artistName = currentSong.artistName;
            int imageResource = currentSong.imageResource;
            int mp3Resource = currentSong.mp3Resource;
            setDisplay(songName, artistName, imageResource);
            System.out.println(songs);
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