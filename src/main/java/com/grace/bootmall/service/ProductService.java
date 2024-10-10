package com.grace.bootmall.service;

import com.grace.bootmall.dto.ProductRequest;
import com.grace.bootmall.dto.ProductRequestParam;
import com.grace.bootmall.dto.ProductUpdateRequest;
import com.grace.bootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductRequestParam productRequestParam);
    Integer countProduct(ProductRequestParam productRequestParam);
    Integer createProduct(ProductRequest productRequest);
    Product getProductById(Integer productId);
    void updateProduct(ProductUpdateRequest productUpdateRequest);
    void deleteProduct(Integer productId);
}
