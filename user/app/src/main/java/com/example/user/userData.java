package com.example.user;

public class userData {

    String Username, Number, Email, Password;

    public userData() {
    }

    public userData(String username, String number, String email) {
        Username = username;
        Number = number;
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    /*public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }*/
}
