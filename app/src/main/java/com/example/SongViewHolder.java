package com.example;

// Variable types
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// Need line at bottom to implement extends RecyclerView.ViewHolder
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Properties
    View itemView;
    ImageView imageView;
    TextView songNameTextView;
    TextView artistNameTextView;
    OnNoteListener onNoteListener;

    // Constructor (Required for extends RecyclerVIew.ViewHolder)
    // Added OnNoteListener to this class so we can click on recyclerview
    public SongViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
        super(itemView);

        this.itemView = itemView;
        imageView = itemView.findViewById(com.example.R.id.imageView);
        songNameTextView = itemView.findViewById(com.example.R.id.songName);
        artistNameTextView = itemView.findViewById(com.example.R.id.artistName);
        // This is to make recyclerview clickable
        this.onNoteListener = onNoteListener;
        itemView.setOnClickListener(this);
        // Once itemView is clicked, it calls onClick method, passing position of item clicked
    }
// This was implemented by View.OnClickListener
    @Override
    public void onClick(View v) {
        // get AdapterPosition gets position of item clicked in recyclerview
        onNoteListener.onNoteClick(getAdapterPosition());
    }
    // This function gets called by onClick when itemView is clicked, connecting back to MainActivity running onNoteClick there
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}

// https://www.youtube.com/watch?v=69C1ljfDvl0  Watched this video to implement OnClickListener for RecyclerView