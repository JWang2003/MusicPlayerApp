package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    // Connect OnNoteListener created in SongViewHolder to adapter
    private SongViewHolder.OnNoteListener mOnNoteListener;

    // Properties
    Context context;
    ArrayList<Song> songs;
    ArrayList<Song> songsCopy;

    // Constructor (Gives list of songs)
    SongAdapter(@NonNull Context context, @NonNull ArrayList<Song> songs, SongViewHolder.OnNoteListener onNoteListener) {
        this.context = context;
        this.songs = songs;
        songsCopy = new ArrayList<>(songs);
        this.mOnNoteListener = onNoteListener;
    }

    // Overridden methods (Required for extend RecyclerView.Adapter)
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This method is called whenever need to create new ViewHolder
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(com.example.R.layout.item_song, parent, false);
        SongViewHolder viewHolder = new SongViewHolder(itemview, mOnNoteListener); // Now takes in a OnNoteListener, passing it to constructor of SongViewHolder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
       // This method is called whenever existing ViewHolder needs to be reused
        // At this point, need to repopulate the ViewHolder
        Song song = songs.get(position);
        holder.imageView.setImageResource(song.imageResource);
        holder.songNameTextView.setText(song.songName);
        holder.artistNameTextView.setText(song.artistName);

    }

    @Override
    public int getItemCount() {
       // This method is called whenever RecyclerView needs to know how many items it needs to display
        return songs.size();
    }

    public void filter(String text) {
        songs.clear();
        if(text.isEmpty()){
            songs.addAll(songsCopy);
        } else{
            text = text.toLowerCase();
            for(Song item: songsCopy){
                if(item.songName.toLowerCase().contains(text) || item.artistName.toLowerCase().contains(text)){
                    songs.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}


