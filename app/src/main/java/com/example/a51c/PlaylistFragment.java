package com.example.a51c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PlaylistFragment extends Fragment {

    public PlaylistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        TextView playlistTextView = view.findViewById(R.id.playlistTextView);
        Button logoutButton = view.findViewById(R.id.playlistLogoutButton);

        AppDatabase db = AppDatabase.getInstance(requireContext());

        List<PlaylistItem> playlist = db.playlistDao().getPlaylist(SessionManager.currentUserId);

        if (playlist.isEmpty()) {
            playlistTextView.setText("No saved videos yet");
        } else {
            StringBuilder builder = new StringBuilder();

            for (PlaylistItem item : playlist) {
                builder.append(item.videoUrl).append("\n\n");
            }

            playlistTextView.setText(builder.toString());
        }

        playlistTextView.setMovementMethod(LinkMovementMethod.getInstance());

        logoutButton.setOnClickListener(v -> {
            SessionManager.currentUserId = -1;

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new LoginFragment())
                    .commit();
        });

        return view;
    }
}