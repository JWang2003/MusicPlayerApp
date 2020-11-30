package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SongViewHolder.OnNoteListener {

    // XML Views
    RecyclerView songsRecyclerView;
    SearchView searchView;
    ImageView pfpDisplay;
    ConstraintLayout mConstraintLayout;
    ImageView flagView;

    // Properties
    Playlist playlist = new Playlist();
    ArrayList<Song> absolutePlaylist; // This is because recyclerview will shrink the playlist, so we need a default one
    SongAdapter songAdapter;
    boolean rickBackground = false;
    ImageButton rickrollButton;
    TextView settings;
    TextView randomSong;
    int pfp;
    int theme;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the shared settings for profile picture and theme
        SharedPreferences settings = getSharedPreferences("Settings", 0);
        pfp = settings.getInt("PFP", 0);
        theme = settings.getInt("Theme", 0);
        flag = settings.getInt("Flag", 0);
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

        // Create & initialize each song

        Song song = new Song ("Happy Rock", "bensound.com",
                R.drawable.happyrock, R.raw.happyrock);
        playlist.songs.add(song);

        song = new Song("Севастопольский вальс", "Ансамбль Александрова",
                R.drawable.redarmy, R.raw.sevastop);

        playlist.songs.add(song);

        song = new Song("A New Beginning", "bensound.com",
                R.drawable.anewbeginning, R.raw.anewbeginning);
        playlist.songs.add(song);

        song = new Song("Summer", "bensound.com",
                R.drawable.summer, R.raw.summer);
        playlist.songs.add(song);

        song = new Song("中华人民共和国国歌", "田汉",
                R.drawable.anthem, R.raw.fanwei);
        playlist.songs.add(song);

        song = new Song("Soviet March", "USSR",
                R.drawable.march, R.raw.sovietmarch);
        playlist.songs.add(song);

        song = new Song ("Das Einheitsfrontlied", "Deutsche Demokratische Republik",
                R.drawable.ddr, R.raw.einheits);
        playlist.songs.add(song);

        song = new Song ("Going Higher", "bensound.com",
                R.drawable.goinghigher, R.raw.goinghigher);
        playlist.songs.add(song);

        song = new Song ("义勇军进行曲", "孙师毅、聂耳",
                R.drawable.china, R.raw.fanwei);
        playlist.songs.add(song);

        song = new Song("Suffering", "Hana Domingo",
                R.drawable.ahyes, R.raw.ricksuffer);
        playlist.songs.add(song);

        // Reusing song variable
        song = new Song("Creative Minds", "bensound.com",
                R.drawable.creativeminds, R.raw.creativeminds);
        playlist.songs.add(song);

        song = new Song("Подмосковные вечера", "Василий Павлович Соловьёв-Седой",
                R.drawable.ussr, R.raw.moscow);
        playlist.songs.add(song);

        song = new Song("Scree", "Hana Domingo",
                R.drawable.bee, R.raw.hanah);
        playlist.songs.add(song);

        song = new Song("Acoustic Breeze", "bensound.com",
                R.drawable.acousticbreeze, R.raw.acousticbreeze);
        // Add first song to to array of songs in the playlist
        playlist.songs.add(song);

        song = new Song("Hey", "bensound.com",
                R.drawable.hey, R.raw.hey);
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
//        randomSong = findViewById(R.id.randomSong);
//        settings = findViewById(R.id.settings);
        pfpDisplay = findViewById(R.id.pfpDisplay);
        searchView = findViewById(R.id.search_bar);
        mConstraintLayout = findViewById(R.id.main);
        flagView = findViewById(R.id.bgFlagView);

        pfpDisplay.setImageResource(pfp);
        switch(theme){
            case 1:
                mConstraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.gradientbackgroundred));
                break;
            case 2:
                mConstraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.gradientbackgroundblue));
                break;
            default:
                System.out.println("Default background colour");
                mConstraintLayout.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.gradientbackgroundred));
        }

        flagView.setImageResource(flag);

        searchView.setOnClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                System.out.println("SearchView registered press");
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
            if (!rickBackground) {
                rickBackground = true;
                flagView.setImageResource(R.drawable.swag_af);
            } else {
                rickBackground = false;
                flagView.setImageResource(flag);
            }
            }
        });
    }

    public void onClickSettings(View view) {
        System.out.println("Clicked settings");
        Intent intent = new Intent(this, SettingsMain.class);
        startActivity(intent);
    }

    public void onClickRandomSong(View view){
        int value = (int) (Math.random() * absolutePlaylist.size());
        System.out.println("Random value = " + value);
        onNoteClick(value);
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