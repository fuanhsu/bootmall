package com.grace.bootmall.dao;

import com.grace.bootmall.dto.OrderRequest;
import com.grace.bootmall.dto.OrderRequestParam;
import com.grace.bootmall.model.Order;
import com.grace.bootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderRequestParam orderRequestParam);
    List<Order> getOrders(OrderRequestParam orderRequestParam);
    List<OrderItem> getOrderItemByOrderId(Integer orderId);
    Integer createOrder(Integer userId, Integer totalAmount);
    Order getOrderById(Integer orderId);
    void createOrderItem(OrderItem orderItem);
}
