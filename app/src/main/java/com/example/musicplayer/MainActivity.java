package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDataModel();
    }

    void populateDataModel(){
        playlist.name = "My Playlist";
        playlist.songs = new ArrayList<Song>();

        Song song = new Song("Acoustic Breeze", "bensound.com", R.drawable.acousticbreeze, R.raw.acousticbreeze);
        playlist.songs.add(song);
    }
    Playlist playlist = new Playlist();
}