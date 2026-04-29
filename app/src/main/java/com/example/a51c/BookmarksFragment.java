package com.example.a51c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;

public class BookmarksFragment extends Fragment {

    public BookmarksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        TextView bookmarksTextView = view.findViewById(R.id.bookmarksTextView);

        Set<String> bookmarks = BookmarkManager.getBookmarks(requireContext());

        if (bookmarks.isEmpty()) {
            bookmarksTextView.setText("No bookmarks yet");
        } else {
            StringBuilder builder = new StringBuilder();

            for (String title : bookmarks) {
                builder.append("• ").append(title).append("\n\n");
            }

            bookmarksTextView.setText(builder.toString());
        }

        return view;
    }
}