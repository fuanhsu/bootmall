package com.grace.bootmall.dao.impl;

import com.grace.bootmall.dao.ProductDao;
import com.grace.bootmall.model.Product;
import com.grace.bootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id, product_name, category, stock, price, image_url, description, created_date, last_modified_date FROM product WHERE product_id = :productId ";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList.size() > 0 ? productList.get(0) : null;
    }

    @Override
    public void updateStock(Integer productId, Integer quantity) {
        String sql = "UPDATE product SET stock = :stock WHERE product_id = :productId ";
        Map<String, Object> map = new HashMap<>();
        map.put("stock", quantity);
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }
}
