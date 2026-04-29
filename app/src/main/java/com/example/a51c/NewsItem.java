package com.example.a51c;
public class NewsItem {
    private String title;
    private String description;
    private String category;
    private int imageResId;

    public NewsItem(String title, String description, String category, int imageResId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.imageResId = imageResId;
    }
    //getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getImageResId() {
        return imageResId;
    }
}
