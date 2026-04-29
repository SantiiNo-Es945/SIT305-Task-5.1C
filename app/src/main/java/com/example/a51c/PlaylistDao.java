package com.example.a51c;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Insert
    void insert(PlaylistItem item);

    @Query("SELECT * FROM playlist_items WHERE userId = :userId")
    List<PlaylistItem> getPlaylist(int userId);
}