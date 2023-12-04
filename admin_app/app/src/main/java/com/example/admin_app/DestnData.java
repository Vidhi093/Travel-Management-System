package com.example.admin_app;

public class DestnData {
    String Title, image, description , date, time, key;

    public DestnData() {
    }

    public DestnData(String title, String image, String description, String key) {
        Title = title;
        this.image = image;
        this.description = description;
        this.key = key;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
