package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDataModel();
    }

    void populateDataModel() {
        Song song = new Song("Acoustic Breeze", "bensound.com",
                           R.drawable.acousticbreeze, R.raw.acousticbreeze);
    }


//Properties
    Playlist playlist = new Playlist();






}