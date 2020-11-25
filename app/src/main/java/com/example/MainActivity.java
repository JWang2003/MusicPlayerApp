package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicapp.R;
import com.example.Playlist;
import com.example.Song;
import com.example.SongAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDataModel();
        connectXMLViews();
        setUpRecyclerView();
        displayCurrentSong();
//        setUpGridLayout();
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
        songsRecyclerView.setLayoutManager(layoutManager);

        // Connect the adapter to the recycler view
        songAdapter = new SongAdapter(this, playlist.songs);
        songsRecyclerView.setAdapter(songAdapter);
    }
//    Replace the setUpRecyclerView function with this one to use GridLayout instead
    void setUpGridLayout(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        songsRecyclerView.setLayoutManager(gridLayoutManager);
        songAdapter = new SongAdapter(this, playlist.songs);
        songsRecyclerView.setAdapter(songAdapter);
    }

    // Connecting variables to XML elements
    void connectXMLViews() {
//        songsRecyclerView = findViewById(R.id.song_list_view);
        songNameTextView = findViewById(R.id.song_name_textview);

    }

    void displayCurrentSong() {
        Song currentSong = playlist.songs.get(currentSongIndex);
        imageView.setImageResource(currentSong.imageResource);
        songNameTextView.setText(currentSong.songName);
        artistNameTextView.setText(currentSong.artistName);
    }


    // XML Views
    RecyclerView songsRecyclerView;
    ImageView imageView;
    TextView songNameTextView;
    TextView artistNameTextView;
    ImageButton previousButton;
    ImageButton pauseButton;
    ImageButton playButton;
    ImageButton nextButton;

    // Properties
    Playlist playlist = new Playlist();
    SongAdapter songAdapter;
    Integer currentSongIndex = 0;


}