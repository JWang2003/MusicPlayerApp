package com.example.musicplayer;

// Variable types
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// Need line at bottom to implement extends RecyclerView.ViewHolder
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.R;

public class SongViewHolder extends RecyclerView.ViewHolder {



    // Constructor (Required for extends RecyclerVIew.ViewHolder)
    public SongViewHolder(@NonNull View itemView) {
        super(itemView);

        this.itemView = itemView;
        imageView = itemView.findViewById(com.example.musicapp.R.id.image_view);
        songNameTextView = itemView.findViewById(com.example.musicapp.R.id.song_name_textview);
        artistNameTextView = itemView.findViewById(com.example.musicapp.R.id.songWriter);

    }

    // Properties
    View itemView;
    ImageView imageView;
    TextView songNameTextView;
    TextView artistNameTextView;

}
