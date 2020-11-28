package com.example;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
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

    protected Song(Parcel in) {
        songName = in.readString();
        artistName = in.readString();
        imageResource = in.readInt();
        mp3Resource = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(artistName);
        dest.writeInt(imageResource);
        dest.writeInt(mp3Resource);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
