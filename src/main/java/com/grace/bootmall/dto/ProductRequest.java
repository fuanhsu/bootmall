package com.grace.bootmall.dto;

import com.grace.bootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {
    @NotNull
    private String productName;
    @NotNull
    private ProductCategory category;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    @NotNull
    private String imageUrl;
    private String description;


    public String getProductName() {
        return productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
