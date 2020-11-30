package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class PlaySongMain extends AppCompatActivity {

    //Initialize variables

    Song currentSong;
    ArrayList<Song> songs;
    int currentSongIndex;
    int rickrollOdds;
    ConstraintLayout mConstraintLayout;

    int theme;

    ImageButton pauseplayButton;
    ImageButton loopButton;
    //checks if Mp3 was playing before stop
    boolean isPlaying = false;
    boolean lastSongWasRick = false;
    //checks if rickRoll is playing in current media player.
    boolean rickRollIsPlaying = false;

    //Initialize mediaPlayer
    MediaPlayer mediaPlayer = null;

    //Stops song when song is at the end
    void  mediaCompleteListener() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!mediaPlayer.isLooping()) {
                    songOver();
                    // Play the current song once Rick Roll ends
                    if (lastSongWasRick){
                        rickRollIsPlaying = false;
                        displayCurrentSong();
                        playCurrentSong();
                        // Automatically play the next when song ends
                    } else if (currentSongIndex < (songs.size() - 1)) {
                            switchSong(currentSongIndex, currentSongIndex + 1);
                            switchPlay();
                            //set arraylist index to 0 when detecting that currentSongIndex is the max arraylist index already.
                    } else {
                        currentSongIndex = 0;
                        switchPlay();
                    }

                }
            }
        });
    }



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

    //COMMENT OUT WHEN WANT TO SHOW REGULAR SONG INFO DURING RICKROLL
    void displayRickRoll() {
        System.out.println("DisplayRickroll");
        TextView songTitle = findViewById(R.id.songName);
        songTitle.setText(R.string.rickroll_songname);
        TextView artistTitle = findViewById(R.id.songWriter);
        artistTitle.setText(R.string.rickroll_artistname);
        ImageView image = findViewById(R.id.songImage);
        image.setImageResource(R.drawable.agony);
    }


    // Roulette that determines if rickroll is played via rickrollOdds
    void RickRoulette() {
        System.out.println("Rickroulette");
        int max = rickrollOdds;
        System.out.println("Rick roll odds are: " + rickrollOdds);
        int min = 1;
        int range = max - min + 1;
        int rickRouletteResult = (int)(Math.random() * range) + min;
        System.out.println("Rickroll result is: " + rickRouletteResult);
        if (rickRouletteResult == 1) {
            System.out.println("Going to display rickroll");
            rickRollIsPlaying = true;
            startRickRoll(); //COMMENT OUT WHEN WANT TO SHOW RICKROLL SONG INFO DURING RICKROLL
            displayRickRoll(); //COMMENT OUT WHEN WANT TO SHOW REGULAR SONG INFO DURING RICKROLL
        } else {
            playCurrentSong();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song_main);
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        rickrollOdds = settings.getInt("rickrollOdds", 6);
        theme = settings.getInt("Theme", 0);
        mConstraintLayout = findViewById(R.id.songscreen);
        if (!(theme == 0)){
            switch(theme){
                case 1:
                    mConstraintLayout.setBackground(ContextCompat.getDrawable(PlaySongMain.this, R.drawable.gradientbackgroundred));
                    break;
                case 2:
                    mConstraintLayout.setBackground(ContextCompat.getDrawable(PlaySongMain.this, R.drawable.gradientbackgroundblue));
                    break;
            }
        }
        setupButtonHandlers();
        System.out.println(rickrollOdds);

        // Process the extra information from intent
        getIncomingIntent();

        RickRoulette();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("Playlist") && getIntent().hasExtra("Index")) {
            songs = getIntent().getParcelableArrayListExtra("Playlist");
            currentSongIndex = getIntent().getIntExtra("Index", 0);
            currentSong = songs.get(currentSongIndex);
            System.out.println("current song index is " + currentSongIndex);

            /*if (!rickRollIsPlaying) */  //COMMENT OUT WHEN WANT TO SHOW REGULAR SONG INFO DURING RICKROLL
              {String songName = currentSong.songName;
                String artistName = currentSong.artistName;
                int imageResource = currentSong.imageResource;
                System.out.println("displaying initial display");
                setInitialDisplay(songName, artistName, imageResource);
                System.out.println(songs);
              }
        }
    }

    private void setInitialDisplay(String songNamed, String artistName, int imageResource) {
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
        ImageButton back = findViewById(R.id.back);
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
        pauseplayButton = findViewById(R.id.pauseplay);
        pauseplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If mediaPlayer does not exist, play current song.
                //Runs when Rickroll is NOT going to play
                if (!rickRollIsPlaying) {
                    playNormal();
                //Runs when Rickroll is going to play
                } else {
                    playRickroll();
                }
            }
        });

        //PreviousSong Button
        ImageButton previousSongButton = findViewById(R.id.prev);
        previousSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rickRollIsPlaying = false;
                // Checks if mediaplayer is still playing
                if (!(mediaPlayer == null)) {
                    songOver();
                }
                if (currentSongIndex > 0 ) {
                    switchSong(currentSongIndex, currentSongIndex - 1);
                    switchPlay();
                } else {
                    currentSongIndex = (songs.size() - 1);
                    switchPlay();
                }
                System.out.println("index: " + currentSongIndex);

                /*if (!rickRollIsPlaying) */ //COMMENT OUT WHEN WANT TO SHOW REGULAR SONG INFO DURING RICKROLL
                    {displayCurrentSong();
                    }
                }
        });


        //NextSong Button
        ImageButton nextSongButton = findViewById(R.id.next);
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Called when next button is tapped
            public void onClick(View view) {
                rickRollIsPlaying = false;
                //Checks if mediaplayer is already playing. If yes, stops current song.
                if (!(mediaPlayer == null)) {
                    songOver();
                }
                //Normal song switching, moves up arraylist index by 1.
                if (currentSongIndex < (songs.size() - 1)) {
                    switchSong(currentSongIndex, currentSongIndex + 1);
                    switchPlay();
                //set arraylist index to 0 when detecting that currentSongIndex is the max arraylist index already.
                } else {
                    currentSongIndex = 0;
                    switchPlay();
                }
                System.out.println("index: " + currentSongIndex);

                /*if (!rickRollIsPlaying) */ //COMMENT OUT WHEN WANT TO SHOW REGULAR SONG INFO DURING RICKROLL
//                { displayCurrentSong();
//                }
            }

        });


        //Shuffle Button
        ImageButton shuffleButton = findViewById(R.id.shuffle);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rickRollIsPlaying = false;
                loopButton.setBackgroundResource(R.drawable.set_loop_on);
                shuffleSong();
                if (!(mediaPlayer==null)) {
                    songOver();
                }
                RickRoulette();
                if (rickRollIsPlaying && isPlaying && mediaPlayer==null) {
                    playCurrentSong();
                    pauseplayButton.setBackgroundResource(R.drawable.pause);
                }
                /*if (!rickRollIsPlaying) */ //COMMENT OUT WHEN WANT TO SHOW REGULAR SONG INFO DURING RICKROLL
//                    {displayCurrentSong();
//                    playCurrentSong();
//                    pauseplayButton.setBackgroundResource(R.drawable.pause);
//                }
            }
        });

        //Loop Button
        loopButton = findViewById(R.id.loop);
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

        }



    //Function that plays song
    void playCurrentSong() {
        lastSongWasRick = false;
        System.out.println("play @ " + currentSongIndex);
        // Check if media player already exists
        if (mediaPlayer == null) {
            // Get the song object corresponding to the current index
            Song currentSong = songs.get(currentSongIndex);
            // Create mediaplayer for current mp3
            mediaPlayer = MediaPlayer.create(PlaySongMain.this, currentSong.mp3Resource);
        }
        // Plays media player mp3
        System.out.println("playCurrentSong mediaPlayer.start()");
        mediaPlayer.start();
        pauseplayButton.setBackgroundResource(R.drawable.pause);
        mediaCompleteListener();
        isPlaying = true;

    }


    //Function that pauses song
    void pauseCurrentSong() {
        System.out.println("pause @ " + currentSongIndex);
        // Check if media player already exists, if media player does not exist, it doesn't run.
        if (mediaPlayer != null) {
            // Pauses media player mp3
            isPlaying = false;
            pauseplayButton.setBackgroundResource(R.drawable.play);
            mediaPlayer.pause();
        }

    }

    //Function that is normally called when pressing nextSong/previousSong button.
    void switchSong(int fromIndex, int toIndex) {
        rickRollIsPlaying = false;
        currentSongIndex = toIndex;
        System.out.println("index: " + currentSongIndex);
//        displayCurrentSong();

    }

    void switchPlay() {
        RickRoulette();
        loopButton.setBackgroundResource(R.drawable.set_loop_on);
        if (!rickRollIsPlaying && isPlaying && mediaPlayer == null) {
            playCurrentSong();
            displayCurrentSong();
            pauseplayButton.setBackgroundResource(R.drawable.pause);
        }
    }

    //Function that loops current song
    void loopSong() {
        System.out.println("LoopSong()");
        if (!(mediaPlayer == null)) {
            if (!(mediaPlayer.isLooping())) {
                System.out.println("song is Looping");
                mediaPlayer.setLooping(true);
                loopButton.setBackgroundResource(R.drawable.set_loop_off);
            } else {
                System.out.println("song is NOT Looping");
                mediaPlayer.setLooping(false);
                loopButton.setBackgroundResource(R.drawable.set_loop_on);
            }
        }
        if (mediaPlayer == null) {
            System.out.println("can't loop when music player == null");
            }
    }


    // Function that stops current song and completely destroys current media player
    void songOver() {
        System.out.println("Song over");
        if (!(mediaPlayer == null)) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            pauseplayButton.setBackgroundResource(R.drawable.play);
        }
    }

    //Plays rickroll mp3
    void startRickRoll() {
        lastSongWasRick = true;
        displayRickRoll();
        System.out.println("startRickRoll");
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(PlaySongMain.this, R.raw.hanaroll);
            pauseplayButton.setBackgroundResource(R.drawable.pause);
            System.out.println("startRickRoll mediaPlayer.start()");
            isPlaying = true;
            mediaPlayer.start();
            mediaCompleteListener();
        }
        isPlaying = true;
        System.out.println("startRickRoll mediaPlayer.start()");
        mediaPlayer.start();
        mediaCompleteListener();
    }


    //What play button does when playing normal song
    void playNormal() {
        lastSongWasRick = false;
        System.out.println("play notRickroll");
        if (mediaPlayer == null) {
            playCurrentSong();
            // If mediaPlayer is not already playing (paused), play current song.
        } else if (!mediaPlayer.isPlaying()) {
            playCurrentSong();
            // If mediaPlayer is already playing, pause the song.
        } else {
            pauseCurrentSong();
        }
    }

    //What play button does when playing rickRoll
    void playRickroll() {
        lastSongWasRick = true;
        if (mediaPlayer == null) {
            startRickRoll();
            pauseplayButton.setBackgroundResource(R.drawable.pause);
            // If mediaPlayer is not already playing (paused), play current song.
        } else if (!mediaPlayer.isPlaying()) {
            startRickRoll();
            pauseplayButton.setBackgroundResource(R.drawable.pause);
            // If mediaPlayer is already playing, pause the song.
        } else {
            pauseCurrentSong();
            pauseplayButton.setBackgroundResource(R.drawable.play);
        }
    }

}
