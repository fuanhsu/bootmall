package com.grace.bootmall.dao.impl;

import com.grace.bootmall.dao.ProductDao;
import com.grace.bootmall.dto.ProductRequest;
import com.grace.bootmall.dto.ProductRequestParam;
import com.grace.bootmall.dto.ProductUpdateRequest;
import com.grace.bootmall.model.Product;
import com.grace.bootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Override
    public List<Product> getProducts(ProductRequestParam productRequestParam) {
        String sql= "SELECT product_id, product_name, category, stock, price, image_url, description, created_date, last_modified_date FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();

        sql = addFilterSql(sql, map, productRequestParam);

        sql += "ORDER BY :orderBy " + productRequestParam.getSort() + " ";
        map.put("orderBy", productRequestParam.getOrderBy());

        sql += "LIMIT :limit OFFSET :offset ";
        map.put("limit", productRequestParam.getLimit());
        map.put("offset", productRequestParam.getOffset());


        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    private String addFilterSql(String sql, Map<String, Object> map, ProductRequestParam productRequestParam) {
        if(productRequestParam.getCategory() != null){
            sql += "AND category = :category ";
            map.put("category", productRequestParam.getCategory().name());
        }
        if(productRequestParam.getSearch() != null) {
            sql += "AND product_name like :search ";
            map.put("search", "%" + productRequestParam.getSearch() + "%");
        }
        return sql;
    }

    @Override
    public Integer countProduct(ProductRequestParam productRequestParam) {
        String sql = "SELECT count(*) FROM product WHERE 1=1 ";
        Map<String, Object> map = new HashMap<>();
        sql = addFilterSql(sql, map, productRequestParam);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }


    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate) ";
        Date now = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId ";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Transactional
    @Override
    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        String sql = "UPDATE product SET ";
        Date now = new Date();
        Map<String, Object> map = new HashMap<>();
        if (productUpdateRequest.getProductName() != null) {
            sql += "product_name = :productName, ";
            map.put("productName", productUpdateRequest.getProductName());
        }
        if (productUpdateRequest.getCategory() != null) {
            sql += "category = :category, ";
            map.put("category", productUpdateRequest.getCategory().name());
        }
        if (productUpdateRequest.getImageUrl() != null) {
            sql += "image_url = :imageUrl, ";
            map.put("imageUrl", productUpdateRequest.getImageUrl());
        }
        if (productUpdateRequest.getPrice() != null) {
            sql += "price = :price, ";
            map.put("price", productUpdateRequest.getPrice());
        }
        if (productUpdateRequest.getStock() != null) {
            sql += "stock = :stock, ";
            map.put("stock", productUpdateRequest.getStock());
        }
        if (productUpdateRequest.getDescription() != null) {
            sql += "description = :description, ";
            map.put("description", productUpdateRequest.getDescription());
        }
        sql += "last_modified_date = :lastModifiedDate ";
        map.put("lastModifiedDate", now);

        sql += " WHERE product_id = :productId ";
        map.put("productId", productUpdateRequest.getProductId());

        namedParameterJdbcTemplate.update(sql, map);
    }
}
