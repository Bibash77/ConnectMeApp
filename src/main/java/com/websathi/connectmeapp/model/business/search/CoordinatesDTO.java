package com.websathi.connectmeapp.model.business.search;

public class CoordinatesDTO {
    private String latitude;
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public CoordinatesDTO(final String latitude, final String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
