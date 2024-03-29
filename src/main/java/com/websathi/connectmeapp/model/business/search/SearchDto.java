package com.websathi.connectmeapp.model.business.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchDto {
    private SearchField searchField;
    private Integer page;
    private Integer limit;

    // Constructors, getters, and setters
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class SearchField {
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
        private Coordinates coordinates;
        private Integer radius;

        private String searchHistory;

        public String getSearchHistory() {
            return this.searchHistory;
        }

        public void setSearchHistory(final String searchHistory) {
            this.searchHistory = searchHistory;
        }

        public static class Coordinates {
            private String latitude;
            private String longitude;

            public Coordinates(final String latitude, final String longitude) {
                this.latitude = latitude;
                this.longitude = longitude;
            }
// Constructors, getters, and setters
        }
    }

    // Constructors, getters, and setters
}
