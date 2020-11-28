package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import java.util.Random;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.R;
import com.example.Playlist;
import com.example.Song;
import com.example.SongAdapter;

public class MainActivity extends AppCompatActivity implements SongViewHolder.OnNoteListener {

    // XML Views
    RecyclerView songsRecyclerView;
    SearchView searchView;

    // Properties
    Playlist playlist = new Playlist();
    ArrayList<Song> absolutePlaylist; // This is because recyclerview will shrink the playlist, so we need a default one
    SongAdapter songAdapter;
    boolean rickrollModeEnabled = false;
    ImageButton rickrollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDataModel();
        connectXMLViews();
        setUpGridLayout();
        setUpButtonHandlers();
        absolutePlaylist = new ArrayList<>(playlist.songs);
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

//   Set up recycler view with gridlayout
    void setUpGridLayout(){
        // span count is how many to place side to side
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        songsRecyclerView.setLayoutManager(gridLayoutManager);
        songAdapter = new SongAdapter(this, playlist.songs, this); // We pass the onNoteListener to the constructor of SongAdapter
        songsRecyclerView.setAdapter(songAdapter);
        songsRecyclerView.setHasFixedSize(true);
    }

    // Connecting variables to XML elements
    void connectXMLViews() {
        songsRecyclerView = findViewById(R.id.recyclerView);
        rickrollButton = findViewById(R.id.rickroll);
        searchView = findViewById(R.id.search_bar);

        searchView.setOnClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
            }
        });

        // Set up the searchview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println(query);
                searchView.clearFocus();
                songAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                songAdapter.filter(newText);
                return true;
            }
        });
    }

    void setUpButtonHandlers() {
        rickrollButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rickrollModeEnabled = true;
            System.out.println(">>> Rickroll mode enabled (forever)!");
            }
        });
    }

    boolean determineRickrollState() {
                //TODO: interface for inputting odds
        int upperLimit = 6;             // temporary constant in absence of user input
        Random r = new Random();
        int randInt = r.nextInt(upperLimit);
        System.out.println(">>> The random integer generated was " + randInt);
        if (randInt == 0) {
            return true;
        } else {
            return false;
        }
    }


// This method was implemented from SongViewHolder
    @Override
    public void onNoteClick(int position) {
        // Navigate to PlaySongMain with current song
        Song currentSong = playlist.songs.get(position);
        int indexOfCurrentSong = absolutePlaylist.indexOf(currentSong);
        Intent intent = new Intent(this, PlaySongMain.class);
        System.out.println("Length of playlist is: " + absolutePlaylist.size());
        intent.putParcelableArrayListExtra("Playlist", absolutePlaylist);
        intent.putExtra("Index", indexOfCurrentSong);
        startActivity(intent);

    }
}