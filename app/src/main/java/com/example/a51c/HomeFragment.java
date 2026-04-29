package com.example.a51c;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText youtubeUrlEditText = view.findViewById(R.id.youtubeUrlEditText);
        Button playButton = view.findViewById(R.id.playButton);
        Button addToPlaylistButton = view.findViewById(R.id.addToPlaylistButton);
        Button myPlaylistButton = view.findViewById(R.id.myPlaylistButton);
        Button logoutButton = view.findViewById(R.id.logoutButton);
        WebView youtubeWebView = view.findViewById(R.id.youtubeWebView);

        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        AppDatabase db = AppDatabase.getInstance(requireContext());

        Bundle args = getArguments();
        if (args != null) {
            String urlFromPlaylist = args.getString("videoUrl");
            if (urlFromPlaylist != null) {
                youtubeUrlEditText.setText(urlFromPlaylist);
                loadYoutubeVideo(youtubeWebView, urlFromPlaylist);
            }
        }

        playButton.setOnClickListener(v -> {
            String url = youtubeUrlEditText.getText().toString();

            if (url.isEmpty()) {
                Toast.makeText(requireContext(), "Enter a YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            loadYoutubeVideo(youtubeWebView, url);
        });

        addToPlaylistButton.setOnClickListener(v -> {
            String url = youtubeUrlEditText.getText().toString();

            if (getVideoId(url) == null) {
                Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            PlaylistItem item = new PlaylistItem();
            item.userId = SessionManager.currentUserId;
            item.videoUrl = url;

            db.playlistDao().insert(item);

            Toast.makeText(requireContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
        });

        myPlaylistButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new PlaylistFragment())
                    .addToBackStack(null)
                    .commit();
        });

        logoutButton.setOnClickListener(v -> {
            SessionManager.currentUserId = -1;

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new LoginFragment())
                    .commit();
        });

        return view;
    }

    private void loadYoutubeVideo(WebView webView, String url) {
        String videoId = getVideoId(url);

        if (videoId == null) {
            Toast.makeText(requireContext(), "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        String embedUrl = "https://www.youtube.com/embed/" + videoId;
        webView.loadUrl(embedUrl);
    }

    private String getVideoId(String url) {
        if (url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2).split("&")[0];
        } else if (url.contains("youtu.be/")) {
            return url.substring(url.indexOf("youtu.be/") + 9).split("\\?")[0];
        }
        return null;
    }
}