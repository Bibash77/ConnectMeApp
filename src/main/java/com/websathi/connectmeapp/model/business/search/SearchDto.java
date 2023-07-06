package com.websathi.connectmeapp.model.business.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String name;
    private String email;
    private String contact;
    private String website;
    private String description;
    private String category;
    private double rating;
    private String location;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String services;
    private String reviews;
    private double latitude;
    private double longitude;
    private int radius;
}
