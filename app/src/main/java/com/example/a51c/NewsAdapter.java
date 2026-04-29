package com.example.a51c;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;
    private OnNewsClickListener listener;
    //making cards clickable
    public interface OnNewsClickListener {
        void onNewsClick(NewsItem newsItem);
    }

    public NewsAdapter(List<NewsItem> newsList, OnNewsClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    //Create card layout
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);

        return new NewsViewHolder(view);
    }

    @Override
    //add title, category and image
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = newsList.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.categoryTextView.setText(item.getCategory());
        holder.imageView.setImageResource(item.getImageResId());
        holder.itemView.setOnClickListener(v -> {
            listener.onNewsClick(item);
        });
    }

    @Override
    //tell RecyclerView how many cards to show
    public int getItemCount() {
        return newsList.size();
    }
    public void updateList(List<NewsItem> newList) {
        newsList = newList;
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView categoryTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.newsImageView);
            titleTextView = itemView.findViewById(R.id.newsTitleTextView);
            categoryTextView = itemView.findViewById(R.id.newsCategoryTextView);
        }
    }
}