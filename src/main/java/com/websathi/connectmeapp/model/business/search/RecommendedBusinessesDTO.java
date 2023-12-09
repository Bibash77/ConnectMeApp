package com.websathi.connectmeapp.model.business.search;

import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.CategoryResponseDto;

import java.util.List;

public class RecommendedBusinessesDTO {
    private List<Business> recommendedBusinesses;

    public CategoryResponseDto[] getRecommendedCategories() {
        return this.recommendedCategories;
    }

    public void setRecommendedCategories(final CategoryResponseDto[] recommendedCategories) {
        this.recommendedCategories = recommendedCategories;
    }

    private CategoryResponseDto[] recommendedCategories;

    public List<Business> getRecommendedBusinesses() {
        return recommendedBusinesses;
    }

    public void setRecommendedBusinesses(List<Business> recommendedBusinesses) {
        this.recommendedBusinesses = recommendedBusinesses;
    }

}