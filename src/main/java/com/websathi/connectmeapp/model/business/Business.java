package com.websathi.connectmeapp.model.business;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business {

    @SerializedName("_id")
    public String id;
    public String name;
    public String email;
    public String contact;
    public String website;
    public String description;
    public List<Service> services;
    public Location location;
    public String category;
    public Float rating;
    public List<Review> reviews;
    public List<String> photos;

    public String distance;

    public Business(final String id, final String name, final String description , final Location location, final Float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.rating = rating;
    }
}
