package com.example.user;

public class hotelBillData {
    String checkin_date, checkout_date, checkin_time, checkout_time,
            rooms, adults, childs, total, hotel_name, hotel_city,
            category, hotel_price ,hotel_address,days, image;

    public hotelBillData() {
    }

    public hotelBillData(String checkin_date, String checkout_date,
                         String checkin_time, String checkout_time,
                         String rooms, String adults, String childs,
                         String total, String hotel_name, String hotel_city,
                         String category, String hotel_price, String hotel_address, String days, String image) {
        this.checkin_date = checkin_date;
        this.checkout_date = checkout_date;
        this.checkin_time = checkin_time;
        this.checkout_time = checkout_time;
        this.rooms = rooms;
        this.adults = adults;
        this.childs = childs;
        this.total = total;
        this.hotel_name = hotel_name;
        this.hotel_city = hotel_city;
        this.category = category;
        this.hotel_price = hotel_price;
        this.hotel_address = hotel_address;
        this.days = days;
        this.image = image;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(String checkin_time) {
        this.checkin_time = checkin_time;
    }

    public String getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(String checkout_time) {
        this.checkout_time = checkout_time;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_city() {
        return hotel_city;
    }

    public void setHotel_city(String hotel_city) {
        this.hotel_city = hotel_city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHotel_price() {
        return hotel_price;
    }

    public void setHotel_price(String hotel_price) {
        this.hotel_price = hotel_price;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
