package com.example.admin_app;

public class AttractionData {
    String Title, Image, Description, key, state, popular_place;

    public AttractionData() {
    }

    public AttractionData(String title, String image, String description, String key, String state, String popular_place) {
        Title = title;
        Image = image;
        Description = description;
        this.key = key;
        this.state = state;
        this.popular_place = popular_place;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPopular_place() {
        return popular_place;
    }

    public void setPopular_place(String popular_place) {
        this.popular_place = popular_place;
    }
}
