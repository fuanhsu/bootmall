package com.grace.bootmall.dao;

import com.grace.bootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
    void updateStock(Integer productId, Integer quantity);
}
