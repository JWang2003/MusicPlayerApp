package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    // Constructor (Gives list of songs)
    SongAdapter(@NonNull Context context, @NonNull ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
    }


    // Overridden methods (Required for extend RecyclerView.Adapter)
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This method is called whenever need to create new ViewHolder
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.item_song, parent, false);
        SongViewHolder viewHolder = new SongViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
       // This method is called whenever existing ViewHolder needs to be reused
        // At this point, need to repopulate the ViewHolder

    }

    @Override
    public int getItemCount() {
       // This method is called whenever RecyclerView needs to know how many items it needs to display
        return songs.size();
    }

    // Properties
    Context context;
    ArrayList<Song> songs;

}
