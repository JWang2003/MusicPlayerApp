package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDataModel();
        setUpRecyclerView();
        connectXMLViews();

    }


    // Initializing Song instances
    void populateDataModel() {
        // Initialize properties of playlist
        playlist.name = "My Playlist";
        playlist.songs = new ArrayList<>();
        // ^ Used to be <Songs> instead of <>


        // Create & initialize first song
        Song song = new Song("Acoustic Breeze", "bensound.com",
                R.drawable.acousticbreeze, R.raw.acousticbreeze);
        // Add first song to to array of songs in the playlist
        playlist.songs.add(song);


        // Reusing song variable
        song = new Song("A New Beginning", "bensound.com",
                R.drawable.anewbeginning, R.raw.anewbeginning);
        playlist.songs.add(song);


        song = new Song("Creative Minds", "bensound.com",
                R.drawable.creativeminds, R.raw.creativeminds);
        playlist.songs.add(song);


        song = new Song ("Going Higher", "bensound.com",
                R.drawable.goinghigher, R.raw.goinghigher);
        playlist.songs.add(song);


        song = new Song ("Happy Rock", "bensound.com",
                R.drawable.happyrock, R.raw.happyrock);
        playlist.songs.add(song);


        song = new Song("Hey", "bensound.com",
                R.drawable.hey, R.raw.hey);
        playlist.songs.add(song);


        song = new Song("Summer", "bensound.com",
                R.drawable.summer, R.raw.summer);
        playlist.songs.add(song);
    }

    // Set up RecyclerView
    void setUpRecyclerView() {
        LinearLayoutManager layoutManager  = new LinearLayoutManager(this);
        songsRecyclerview.setLayoutManager(layoutManager);

        // Connect the adapter to the recycler view

    }

    // Connecting variables to XML elements
    void connectXMLViews() {
        songsRecyclerview = findViewById(R.id.song_list_view);
        imageView = findViewById(R.id.cover_image);
        songNameTextView = findViewById(R.id.song_name_textview);
        artistNameTextView = findViewById(R.id.artist_name_textview);
        previousButton = findViewById(R.id.previous_button);
        pauseButton = findViewById(R.id.pause_button);
        playButton = findViewById(R.id.play_button);
        nextButton = findViewById(R.id.next_button);

    }




    // XML Views
    RecyclerView songsRecyclerview;
    ImageView imageView;
    TextView songNameTextView;
    TextView artistNameTextView;
    ImageButton previousButton;
    ImageButton pauseButton;
    ImageButton playButton;
    ImageButton nextButton;




    // Properties
    Playlist playlist = new Playlist();


}