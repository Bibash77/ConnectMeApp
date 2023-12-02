package com.websathi.connectmeapp.BL;

import com.websathi.connectmeapp.model.business.Location;

public class SearchConfig {
    private String radius;

    public String getSettingName() {
        return this.settingName;
    }

    public void setSettingName(final String settingName) {
        this.settingName = settingName;
    }

    private String settingName;
    private String categories;
    private String longitude;
    private String latitude;
    private String rating;

    private String formattedAddress;
    public SearchConfig() {
    }

    public SearchConfig(final String radius, final String categories, final String longitude, final String latitude, final String rating) {
        this.radius = radius;
        this.categories = categories;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = rating;
    }

    public String getRadius() {
        return this.radius;
    }

    public void setRadius(final String radius) {
        this.radius = radius;
    }

    public String getCategories() {
        return this.categories;
    }

    public void setCategories(final String categories) {
        this.categories = categories;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    public void setFormattedAddress(final String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }
}
