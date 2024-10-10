package com.grace.bootmall.dao;

import com.grace.bootmall.dto.ProductRequest;
import com.grace.bootmall.dto.ProductRequestParam;
import com.grace.bootmall.dto.ProductUpdateRequest;
import com.grace.bootmall.model.Product;

import java.util.List;

public interface ProductDao {

    Product getProductById(Integer productId);
    void updateStock(Integer productId, Integer quantity);
    List<Product> getProducts(ProductRequestParam productRequestParam);
    Integer countProduct(ProductRequestParam productRequestParam);
    Integer createProduct(ProductRequest productRequest);
    void deleteProduct(Integer productId);
    void updateProduct(ProductUpdateRequest productUpdateRequest);
}
