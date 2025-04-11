package com.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CategoryDTO {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int categoryId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
