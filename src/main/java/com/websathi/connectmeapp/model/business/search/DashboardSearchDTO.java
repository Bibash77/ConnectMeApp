package com.websathi.connectmeapp.model.business.search;

public class DashboardSearchDTO {
    private CoordinatesDTO coordinates;
    private String name;

    public CoordinatesDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesDTO coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
