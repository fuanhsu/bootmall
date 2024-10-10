package com.grace.bootmall.dto;

import com.grace.bootmall.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

public class ProductUpdateRequest {
    private Integer productId;
    private String productName;
    private ProductCategory category;
    private Integer price;
    private Integer stock;
    private String description;
    private String imageUrl;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

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
