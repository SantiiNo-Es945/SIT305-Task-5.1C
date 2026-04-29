package com.example.a51c;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView featuredRecyclerView;
    RecyclerView latestRecyclerView;

    ArrayList<NewsItem> featuredList;
    ArrayList<NewsItem> latestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showing login screen first
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new LoginFragment())
                    .commit();
        }
        Button openBookmarksButton = findViewById(R.id.openBookmarksButton);

        openBookmarksButton.setOnClickListener(v -> {
            BookmarksFragment bookmarksFragment = new BookmarksFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, bookmarksFragment)
                    .addToBackStack(null)
                    .commit();
        });

        featuredRecyclerView = findViewById(R.id.featuredRecyclerView);
        latestRecyclerView = findViewById(R.id.latestRecyclerView);
        EditText searchEditText = findViewById(R.id.searchEditText);

        featuredList = new ArrayList<>();
        latestList = new ArrayList<>();

        addDummyData();
        //open detail screen
        NewsAdapter featuredAdapter = new NewsAdapter(featuredList, newsItem -> {
            openDetailFragment(newsItem);
        });
        NewsAdapter latestAdapter = new NewsAdapter(latestList, newsItem -> {
            openDetailFragment(newsItem);
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<NewsItem> filteredList = new ArrayList<>();

                for (NewsItem item : latestList) {
                    if (item.getCategory().toLowerCase().contains(s.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }

                latestAdapter.updateList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        //featured matches scroll sideways
        featuredRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        latestRecyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        featuredRecyclerView.setAdapter(featuredAdapter);
        latestRecyclerView.setAdapter(latestAdapter);
    }

    private void openDetailFragment(NewsItem newsItem) {
        DetailFragment detailFragment = DetailFragment.newInstance(
                newsItem.getTitle(),
                newsItem.getDescription(),
                newsItem.getImageResId()
        );

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void addDummyData() {
        featuredList.add(new NewsItem(
                "Melbourne wins final match",
                "Melbourne finished the game with a strong performance.",
                "Football",
                R.drawable.ic_launcher_background
        ));

        featuredList.add(new NewsItem(
                "Cricket team prepares for season",
                "The team is training hard before the next big tournament.",
                "Cricket",
                R.drawable.ic_launcher_background
        ));

        latestList.add(new NewsItem(
                "Basketball star scores 40 points",
                "A great performance helped the team win the match.",
                "Basketball",
                R.drawable.ic_launcher_background
        ));

        latestList.add(new NewsItem(
                "Football coach announces new squad",
                "The coach selected young players for the next game.",
                "Football",
                R.drawable.ic_launcher_background
        ));

        latestList.add(new NewsItem(
                "Cricket fans excited for finals",
                "The final match is expected to attract thousands of fans.",
                "Cricket",
                R.drawable.ic_launcher_background
        ));
    }
}