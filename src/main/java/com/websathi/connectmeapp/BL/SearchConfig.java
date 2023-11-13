package com.websathi.connectmeapp.BL;

import com.websathi.connectmeapp.model.business.Location;

import lombok.Data;

@Data
public class SearchConfig {

    private Float locationRadius;

    private Float rating;

    private Location selectLocation;
}
