package com.example.a51c;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class BookmarkManager {

    private static final String PREF_NAME = "bookmarks";
    private static final String KEY_BOOKMARKS = "saved_stories";

    public static void saveBookmark(Context context, String title) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Set<String> bookmarks = new HashSet<>(
                prefs.getStringSet(KEY_BOOKMARKS, new HashSet<>())
        );

        bookmarks.add(title);

        prefs.edit().putStringSet(KEY_BOOKMARKS, bookmarks).apply();
    }

    public static Set<String> getBookmarks(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getStringSet(KEY_BOOKMARKS, new HashSet<>());
    }
}