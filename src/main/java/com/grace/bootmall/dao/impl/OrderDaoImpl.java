package com.grace.bootmall.dao.impl;

import com.grace.bootmall.dao.OrderDao;
import com.grace.bootmall.rowmapper.OrderItemRowMapper;
import com.grace.bootmall.rowmapper.OrderRowMapper;
import com.grace.bootmall.dto.OrderRequestParam;
import com.grace.bootmall.model.Order;
import com.grace.bootmall.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrder(OrderRequestParam orderRequestParam) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1 ";
        Map<String, Object>  map = new HashMap<>();

        sql = addFilterSql(sql, map, orderRequestParam);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    private String addFilterSql(String sql, Map<String, Object> map, OrderRequestParam orderRequestParam) {
        if (orderRequestParam.getUserId() != null){
            sql += "AND user_id = :userId ";
            map.put("userId", orderRequestParam.getUserId());
        }
        return sql;
    }

    @Override
    public List<Order> getOrders(OrderRequestParam orderRequestParam) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM  `order`  WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();
        sql = addFilterSql(sql, map, orderRequestParam);
        sql += "ORDER BY created_date DESC ";

        sql += "LIMIT :limit OFFSET :offset ";

        map.put("limit", orderRequestParam.getLimit());
        map.put("offset", orderRequestParam.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        String sql = "SELECT order_id, order_item_id, product_id, quantity, amount FROM order_item WHERE order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate) ";
        Date now = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM  `order`  WHERE order_id = :orderId ";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return orderList.size() > 0 ? orderList.get(0) : null;
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (:orderId, :productId, :quantity, :amount) ";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderItem.getOrderId());
        map.put("productId", orderItem.getProductId());
        map.put("quantity", orderItem.getQuantity());
        map.put("amount", orderItem.getAmount());
        KeyHolder KeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), KeyHolder);
    }
}
