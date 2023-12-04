package com.example.admin_app;

public class advisoryData {
    String State;
    String Place;
    String Wtt;
    String Ttd;
    String Image;
    String Uniquekey;
    String Description;
    public advisoryData() {
    }

    public advisoryData(String state, String place, String wtt, String ttd, String image, String uniquekey, String description) {
        State = state;
        Place = place;
        Wtt = wtt;
        Ttd = ttd;
        Image = image;
        Uniquekey = uniquekey;
        Description = description;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getWtt() {
        return Wtt;
    }

    public void setWtt(String wtt) {
        Wtt = wtt;
    }

    public String getTtd() {
        return Ttd;
    }

    public void setTtd(String ttd) {
        Ttd = ttd;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUniquekey() {
        return Uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        Uniquekey = uniquekey;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
