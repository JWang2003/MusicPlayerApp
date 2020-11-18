package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("William");
        System.out.println("gaming,");
        System.out.println("Jimmmmmmmmy");
    }

    //Properties
    Playlist playlist = new Playlist();



}