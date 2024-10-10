package com.grace.bootmall.service;

import com.grace.bootmall.dto.OrderRequest;
import com.grace.bootmall.dto.OrderRequestParam;
import com.grace.bootmall.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders(OrderRequestParam orderRequestParam);
    Integer countOrder(OrderRequestParam orderRequestParam);
    Integer createOrder(Integer userId, OrderRequest orderRequest);
    Order getOrderById(Integer orderId);
}
