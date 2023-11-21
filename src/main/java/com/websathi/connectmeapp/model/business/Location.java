package com.websathi.connectmeapp.model.business;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    public String type;

    public String formattedAddress;
    public List<Double> coordinates;
}
