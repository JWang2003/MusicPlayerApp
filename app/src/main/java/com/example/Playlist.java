package com.example;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.Song;

import java.util.ArrayList;
import java.util.stream.Stream;


public class Playlist extends ArrayList<Parcelable> {
    String name;
    ArrayList<Song> songs;

    @NonNull
    @Override
    public Stream<Parcelable> stream() {
        return null;
    }
}
