package com.websathi.connectmeapp.model.business;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @SerializedName("_id")
    public String id;
    public String name;
    public String comment;
    public Double rating;
}
