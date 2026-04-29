package com.example.a51c;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_items")
public class PlaylistItem {

    @PrimaryKey(autoGenerate = true)
    public int id;
//each user have their own playlist
    public int userId;
    public String videoUrl;
}