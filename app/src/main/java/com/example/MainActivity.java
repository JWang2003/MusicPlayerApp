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
    ImageView pfpDisplay;

    // Properties
    Playlist playlist = new Playlist();
    ArrayList<Song> absolutePlaylist; // This is because recyclerview will shrink the playlist, so we need a default one
    SongAdapter songAdapter;
    boolean rickrollModeEnabled = false;
    ImageButton rickrollButton;
    TextView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDataModel();
        connectXMLViews();
        setUpGridLayout();
//        setUpButtonHandlers();
        absolutePlaylist = new ArrayList<>(playlist.songs);
    }




    // Initializing Song instances
    void populateDataModel() {
        // Initialize properties of playlist
        playlist.name = "My Playlist";
        playlist.songs = new ArrayList<>();
        // ^ Used to be <Songs> instead of <>


        // Create & initialize first song
        Song song = new Song("Севастопольский вальс", "Ансамбль Александрова",
                R.drawable.redarmy, R.raw.sevastop);
        // Add first song to to array of songs in the playlist
        playlist.songs.add(song);


        // Reusing song variable
        song = new Song("中华人民共和国国歌", "田汉",
                R.drawable.anthem, R.raw.fanwei);
        playlist.songs.add(song);


        song = new Song("Soviet March", "USSR",
                R.drawable.march, R.raw.sovietmarch);
        playlist.songs.add(song);


        song = new Song ("Das Einheitsfrontlied", "Deutsche Demokratische Republik",
                R.drawable.ddr, R.raw.einheits);
        playlist.songs.add(song);


        song = new Song ("义勇军进行曲", "孙师毅、聂耳",
                R.drawable.china, R.raw.fanwei);
        playlist.songs.add(song);


        song = new Song("Подмосковные вечера", "Василий Павлович Соловьёв-Седой",
                R.drawable.ussr, R.raw.moscow);
        playlist.songs.add(song);

        song = new Song("Scree", "Hana Domingo",
                R.drawable.bee, R.raw.hanah);
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
        settings = findViewById(R.id.settings);
        pfpDisplay = findViewById(R.id.pfpDisplay);
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
            if (!rickrollModeEnabled) {
                rickrollModeEnabled = true;
                System.out.println(">>> Rickroll mode enabled!");
            } else {
                rickrollModeEnabled = false;
                System.out.println(">>> Rickroll mode disabled!");
                }
            }
        });
    }

    public void onClickSettings(View view) {
        System.out.println("Clicked settings");
        Intent intent = new Intent(this, SettingsMain.class);
        startActivity(intent);
    }

    boolean determineRickrollState() {

        Bundle extras = getIntent().getExtras();
        int upperLimit = extras.getInt("EXTRA_RICKROLL_ODDS_UPPERLIMIT");
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