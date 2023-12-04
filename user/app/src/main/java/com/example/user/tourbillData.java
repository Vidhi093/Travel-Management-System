package com.example.user;

public class tourbillData {

    String start, end, adults, childs, days, total, name, price, contact, state, city, image;
    public tourbillData() {
    }

    public tourbillData(String start, String end, String adults, String childs, String days, String total, String name, String price, String contact, String state, String city, String image) {
        this.start = start;
        this.end = end;
        this.adults = adults;
        this.childs = childs;
        this.days = days;
        this.total = total;
        this.name = name;
        this.price = price;
        this.contact = contact;
        this.state = state;
        this.city = city;
        this.image = image;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChilds() {
        return childs;
    }

    public void setChilds(String childs) {
        this.childs = childs;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
