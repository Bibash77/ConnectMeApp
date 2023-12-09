package com.websathi.connectmeapp.model.business;

public class CategoryResponseDto {
    private String name;
    private int imgId;

    private String icon;

    public CategoryResponseDto(final String name, final int imgId, final String icon) {
        this.name = name;
        this.imgId = imgId;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }  
    public void setName(String name) {
        this.name = name;
    }  
    public int getImgId() {  
        return imgId;  
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;  
    }  
}  