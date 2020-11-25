package com.example;

public class Song {
    String songName;
    String artistName;
    int imageResource;
    int mp3Resource;

    Song(String songName, String artistName, int imageResource, int mp3Resource){
        this.songName = songName;
        this.artistName = artistName;
        this.imageResource = imageResource;
        this.mp3Resource = mp3Resource;
    }
}
