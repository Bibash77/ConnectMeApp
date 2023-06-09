package com.websathi.connectmeapp.model;

public class Business {
    private String name;
    private String address;
    private int photo;

    public Business(String name, String address, int photo) {
        this.name = name;
        this.address = address;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoto() {
        return photo;
    }
}
