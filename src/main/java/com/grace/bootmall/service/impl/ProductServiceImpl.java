package com.grace.bootmall.service.impl;

import com.grace.bootmall.dao.ProductDao;
import com.grace.bootmall.dto.ProductRequest;
import com.grace.bootmall.dto.ProductRequestParam;
import com.grace.bootmall.dto.ProductUpdateRequest;
import com.grace.bootmall.model.Product;
import com.grace.bootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductRequestParam productRequestParam) {
        return productDao.getProducts(productRequestParam);
    }

    @Override
    public Integer countProduct(ProductRequestParam productRequestParam) {
        return productDao.countProduct(productRequestParam);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        productDao.updateProduct(productUpdateRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }
}
