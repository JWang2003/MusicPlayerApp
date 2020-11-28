package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;

import java.util.ArrayList;

public class PlaySongMain extends AppCompatActivity {

    int mp3Resource;
    Song currentSong;
    ArrayList<Song> songs;
    int currentSongIndex;


    /*// Initialize button variables

     shuffleButton;
    ImageButton prevButton;
    ImageButton pauseplayButton;
    ImageButton nextButton;
    ImageButton loopButton;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Created oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_main);
        setupButtonHandlers();

        // Process the extra information from intent
        getIncomingIntent();


        // Go back to mainactivity
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PlaySongMain.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("Playlist") && getIntent().hasExtra("Index")) {
            songs = getIntent().getParcelableArrayListExtra("Playlist");
            currentSongIndex = getIntent().getIntExtra("Index", 0);
            currentSong = songs.get(currentSongIndex);
            System.out.println("current song index is " + currentSongIndex);
            String songName = currentSong.songName;
            String artistName = currentSong.artistName;
            int imageResource = currentSong.imageResource;
            int mp3Resource = currentSong.mp3Resource;
            setDisplay(songName, artistName, imageResource);
            System.out.println(songs);
        }
    }

    private void setDisplay(String songNamed, String artistName, int imageResource) {
        TextView songTitle = findViewById(R.id.songName);
        songTitle.setText(songNamed);
        TextView artistTitle = findViewById(R.id.songWriter);
        artistTitle.setText(artistName);
        ImageView image = findViewById(R.id.songImage);
        image.setImageResource(imageResource);
    }



    void setupButtonHandlers() {
        ImageButton shuffleButton = findViewById(R.id.shuffle);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Called when shuffle button is tapped
                System.out.println("shuffle has been tapped");
            }
        });

        ImageButton previousButton = findViewById(R.id.prev);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Called when prev button is tapped
                System.out.println("previous button has been tapped");
            }
        });

        final ImageButton pauseplayButton = findViewById(R.id.pauseplay);
        pauseplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If mediaPlayer does not exist, play current song.
                if (mediaPlayer == null) {
                    playCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.);
                    // If mediaPlayer is not already playing (paused), play current song.
                } else if (!mediaPlayer.isPlaying()) {
                    playCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.);
                    // If mediaPlayer is already playing, pause the song.
                } else {
                    pauseCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.
                }
            }

        });


        ImageButton nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Called when next button is tapped
                System.out.println("next");
            }
        });

        ImageButton loopButton = findViewById(R.id.loop);
        loopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Called when loop button is tapped
                System.out.println("loop");
            }
        });

        }





    void playCurrentSong() {
        System.out.println("play");
        // Check if media player already exists
        if (mediaPlayer == null) {
            // Get the song object corresponding to the current index
            Song currentSong = songs.get(currentSongIndex);
            // Create mediaplayer for current mp3
            mediaPlayer = MediaPlayer.create(PlaySongMain.this, currentSong.mp3Resource);
        }
        // Play the song
        mediaPlayer.start();
        //

    }

    void pauseCurrentSong() {
        System.out.println("pause");
        // Check if media player already exists
        if (mediaPlayer != null) {

            // Pauses media player mp3
            mediaPlayer.pause();

        }


    }


    MediaPlayer mediaPlayer = null;

}