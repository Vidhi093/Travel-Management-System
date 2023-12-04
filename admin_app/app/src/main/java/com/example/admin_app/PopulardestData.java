package com.example.admin_app;

public class PopulardestData {
    String Title, Image, Description, key;

    public PopulardestData() {
    }

    public PopulardestData(String title, String image, String description, String key) {
        Title = title;
        Image = image;
        Description = description;
        this.key = key;
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
}
