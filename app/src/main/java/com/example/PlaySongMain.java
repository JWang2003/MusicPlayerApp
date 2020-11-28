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
import java.util.Random;

public class PlaySongMain extends AppCompatActivity {

    int mp3Resource;
    Song currentSong;


    ArrayList<Song> songs;
    int currentSongIndex;


    //Initialize mediaPlayer
    MediaPlayer mediaPlayer = null;




    //TODO: [J] Ask william how the heck this stupid displayCurrentSong works
    private void displayCurrentSong() {
        /*String songName = currentSong.songName;
        String artistName = currentSong.artistName;
        int imageResource = currentSong.imageResource;
        int mp3Resource = currentSong.mp3Resource;*/

        TextView songTitle = findViewById(R.id.songName);
        songTitle.setText(currentSong.songName);

        TextView artistTitle = findViewById(R.id.songWriter);
        artistTitle.setText(currentSong.artistName);

        ImageView image = findViewById(R.id.songImage);
        image.setImageResource(currentSong.imageResource);
    }



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
                void shuffleSong();
            }
        });


        ImageButton previousButton = findViewById(R.id.prev);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Called when prev button is tapped
                System.out.println("previous button has been tapped");
                currentSongIndex--;
                if (!(mediaPlayer == null)) {
                    songOver();
                }

                switchSong(currentSongIndex, currentSongIndex - 1);

                /*if(currentSongIndex >= 0 ) {
                    currentSongIndex--;
                } else {
                    currentSongIndex = (songs.size() - 1);
                }*/
            }
        });


        final ImageButton pauseplayButton = findViewById(R.id.pauseplay);
        pauseplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If mediaPlayer does not exist, play current song.
                if (mediaPlayer == null) {
                    playCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.pause);
                    // If mediaPlayer is not already playing (paused), play current song.
                } else if (!mediaPlayer.isPlaying()) {
                    playCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.pause);
                    // If mediaPlayer is already playing, pause the song.
                } else {
                    pauseCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.play);
                }
            }

        });


        ImageButton nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Called when next button is tapped

                if (!(mediaPlayer == null)) {
                    songOver();
                }
                switchSong(currentSongIndex, currentSongIndex + 1);

                /*if(currentSongIndex < (songs.size() - 1) ) {
                    currentSongIndex++;
                } else {
                    currentSongIndex = 0;
                }*/
            }
        });

        ImageButton loopButton = findViewById(R.id.loop);
        loopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Called when loop button is tapped
                System.out.println("loop");
                if (!(mediaPlayer == null)) {
                    if (!(mediaPlayer.isLooping())) {
                        mediaPlayer.setLooping(true);
                    } else {
                        mediaPlayer.setLooping(false);
                    }
                if (mediaPlayer == null) {
                    System.out.println("can't loop when music player == null");
                    }
                }
            }
        });

        }



    void shuffleSong() {
        boolean shuffled = true;
        int upperLimit = songs.size() - 1;
        Random random = new Random();
        int rand = random.nextInt(upperLimit);
        rand = currentSongIndex;

        //TODO: [J] apply set display current song for shuffle song
        setDisplay();

    }

    void playCurrentSong() {
        System.out.println("play @ " + currentSongIndex);
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
        System.out.println("pause @ " + currentSongIndex);
        // Check if media player already exists
        if (mediaPlayer != null) {

            // Pauses media player mp3
            mediaPlayer.pause();
        }

    }

    void switchSong(int fromIndex, int toIndex) {
        currentSongIndex = toIndex;
        //TODO: [J] apply display current song to switch song
        displayCurrentSong();

    }




    void songOver() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }


}
