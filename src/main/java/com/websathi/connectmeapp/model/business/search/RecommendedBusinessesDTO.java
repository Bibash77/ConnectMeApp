package com.websathi.connectmeapp.model.business.search;

import com.websathi.connectmeapp.model.business.Business;

import java.util.List;

public class RecommendedBusinessesDTO {
    private List<Business> recommendedBusinesses;
    private List<String> recommendedCategories;

    public List<Business> getRecommendedBusinesses() {
        return recommendedBusinesses;
    }

    public void setRecommendedBusinesses(List<Business> recommendedBusinesses) {
        this.recommendedBusinesses = recommendedBusinesses;
    }

    public List<String> getRecommendedCategories() {
        return recommendedCategories;
    }

    public void setRecommendedCategories(List<String> recommendedCategories) {
        this.recommendedCategories = recommendedCategories;
    }
}