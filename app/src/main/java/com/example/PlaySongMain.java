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

    //Initialize variables
    int mp3Resource;
    Song currentSong;
    ArrayList<Song> songs;
    int currentSongIndex;

    //checks if Mp3 was playing before stop
    boolean isPlaying = false;

    //Initialize mediaPlayer
    MediaPlayer mediaPlayer = null;


    //Displays currentSong when switching songs
    private void displayCurrentSong() {
        currentSong = songs.get(currentSongIndex);
        TextView songTitle = findViewById(R.id.songName);
        songTitle.setText(currentSong.songName);
        TextView artistTitle = findViewById(R.id.songWriter);
        artistTitle.setText(currentSong.artistName);
        ImageView image = findViewById(R.id.songImage);
        image.setImageResource(currentSong.imageResource);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_main);
        setupButtonHandlers();

        // Process the extra information from intent
        getIncomingIntent();
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
        //Top bar [Start]
        //Back Button
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PlaySongMain.this, MainActivity.class);
                startActivity(intent);
                songOver();
                isPlaying = false;
            }
        });

        //Top Bar [end]
        //Song control buttons [Start]
        //PausePlay Button
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
                    isPlaying = true;
                    // If mediaPlayer is already playing, pause the song.
                } else {
                    pauseCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.play);
                    isPlaying = false;
                }
            }

        });

        //PreviousSong Button
        ImageButton previousSongButton = findViewById(R.id.prev);
        previousSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checks if mediaplayer is still playing
                if (!(mediaPlayer == null)) {
                    songOver();
                }
                if(currentSongIndex > 0 ) {
                    switchSong(currentSongIndex, currentSongIndex - 1);
                    /*DEBUG: System.out.println("normal prevSong ");*/
                    if (isPlaying && mediaPlayer==null) {
                        playCurrentSong();
                        pauseplayButton.setBackgroundResource(R.drawable.pause);
                    }
                } else {
                    currentSongIndex = (songs.size() - 1);
                    System.out.println("index: " + currentSongIndex);
                    /*DEBUG: System.out.println("special prevSong");*/
                    displayCurrentSong();
                    if (isPlaying && mediaPlayer==null) {
                        playCurrentSong();
                        pauseplayButton.setBackgroundResource(R.drawable.pause);
                    }
                }
            }
        });



        //NextSong Button
        ImageButton nextSongButton = findViewById(R.id.next);
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Called when next button is tapped
            public void onClick(View view) {
                //Checks if mediaplayer is already playing. If yes, stops current song.
                if (!(mediaPlayer == null)) {
                    songOver();
                }
                //Normal song switching, moves up arraylist index by 1.
                if(currentSongIndex < (songs.size() - 1) ) {
                    switchSong(currentSongIndex, currentSongIndex + 1);
                    if (isPlaying && mediaPlayer==null) {
                        playCurrentSong();
                        pauseplayButton.setBackgroundResource(R.drawable.pause);
                    }

                  //set arraylist index to 0 when detecting that currentSongIndex is the max arraylist index already.
                } else {
                    currentSongIndex = 0;
                    System.out.println("index: " + currentSongIndex);
                    displayCurrentSong();
                      if (isPlaying && mediaPlayer==null) {
                        playCurrentSong();
                        pauseplayButton.setBackgroundResource(R.drawable.pause);
                    }
                }
            }
        });

        //Shuffle Button
        ImageButton shuffleButton = findViewById(R.id.shuffle);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Called when shuffle button is tapped
                System.out.println("shuffle has been tapped");
                shuffleSong();
                if (!(mediaPlayer==null)) {
                    songOver();
                }
                if (isPlaying && mediaPlayer==null) {
                    playCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.pause);
                }

            }
        });

        //Loop Button
        final ImageButton loopButton = findViewById(R.id.loop);

        loopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Called when loop button is tapped
                System.out.println("loop");
                loopSong();
            }
        });
        //Song control buttons [end]
        }



    //Function that shuffles songs
    void shuffleSong() {
        //Previous song index tells whether currentSongIndex after random is still the same as before
        int previousSongIndex;
        previousSongIndex = currentSongIndex;
        //Loop prevents shuffling to same song with the help of previousSongIndex
        while (true) {
            int upperLimit = songs.size() - 1;
            Random random = new Random();
            int rand = random.nextInt(upperLimit);
            currentSongIndex = rand;
            System.out.println("Shuffled song. index now at " + currentSongIndex);
            //Breaks loop once detect that currentSongIndex does not equal previousSongIndex
            if (currentSongIndex != previousSongIndex) break;
            }
        //Displays current song
        displayCurrentSong();
        }



    //Function that plays song
    void playCurrentSong() {
        System.out.println("play @ " + currentSongIndex);
        // Check if media player already exists
        if (mediaPlayer == null) {
            // Get the song object corresponding to the current index
            Song currentSong = songs.get(currentSongIndex);
            // Create mediaplayer for current mp3
            mediaPlayer = MediaPlayer.create(PlaySongMain.this, currentSong.mp3Resource);
        }
        // Plays media player mp3
        mediaPlayer.start();
        isPlaying = true;
    }


    //Function that pauses song
    void pauseCurrentSong() {
        System.out.println("pause @ " + currentSongIndex);
        // Check if media player already exists, if media player does not exist, it doesn't run.
        if (mediaPlayer != null) {
            // Pauses media player mp3
            mediaPlayer.pause();
        }

    }

    //Function that is normally called when pressing nextSong/previousSong button.
    void switchSong(int fromIndex, int toIndex) {
        currentSongIndex = toIndex;
        System.out.println("index: " + currentSongIndex);
        displayCurrentSong();

    }

    //Function that loops current song
    void loopSong() {
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

    // Function that stops current song and completely destroys current media player
    void songOver() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
