package com.example.a51c;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.Toast;

public class DetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE = "image";

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String title, String description, int imageResId) {
        DetailFragment fragment = new DetailFragment();
        //send the data
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_IMAGE, imageResId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView detailImageView = view.findViewById(R.id.detailImageView);
        TextView detailTitleTextView = view.findViewById(R.id.detailTitleTextView);
        TextView detailDescriptionTextView = view.findViewById(R.id.detailDescriptionTextView);
        RecyclerView relatedRecyclerView = view.findViewById(R.id.relatedRecyclerView);
        Button bookmarkButton = view.findViewById(R.id.bookmarkButton);

        String title = "";
        String description = "";
        int image = R.drawable.ic_launcher_background;

        if (getArguments() != null) {//read the data
            title = getArguments().getString(ARG_TITLE);
            description = getArguments().getString(ARG_DESCRIPTION);
            image = getArguments().getInt(ARG_IMAGE);

            detailTitleTextView.setText(title);
            detailDescriptionTextView.setText(description);
            detailImageView.setImageResource(image);
        }
        ArrayList<NewsItem> relatedList = new ArrayList<>();

        relatedList.add(new NewsItem(
                "Related: Team training update",
                "A short update about the team's latest training session.",
                "Football",
                R.drawable.ic_launcher_background
        ));

        relatedList.add(new NewsItem(
                "Related: Player interview",
                "A player shares thoughts about the next match.",
                "Basketball",
                R.drawable.ic_launcher_background
        ));

        relatedList.add(new NewsItem(
                "Related: Match preview",
                "A preview of the next important game.",
                "Cricket",
                R.drawable.ic_launcher_background
        ));

        NewsAdapter relatedAdapter = new NewsAdapter(relatedList, newsItem -> {
            DetailFragment newDetailFragment = DetailFragment.newInstance(
                    newsItem.getTitle(),
                    newsItem.getDescription(),
                    newsItem.getImageResId()
            );

            FragmentTransaction transaction = requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();

            transaction.replace(android.R.id.content, newDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        relatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        relatedRecyclerView.setAdapter(relatedAdapter);
        //story title saved
        String finalTitle = title;

        bookmarkButton.setOnClickListener(v -> {
            BookmarkManager.saveBookmark(requireContext(), finalTitle);
            Toast.makeText(requireContext(), "Bookmarked", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}