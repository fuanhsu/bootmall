package com.grace.bootmall.service.impl;

import com.grace.bootmall.dao.OrderDao;
import com.grace.bootmall.dao.ProductDao;
import com.grace.bootmall.dao.UserDao;
import com.grace.bootmall.dto.BuyItem;
import com.grace.bootmall.dto.OrderRequest;
import com.grace.bootmall.dto.OrderRequestParam;
import com.grace.bootmall.model.Order;
import com.grace.bootmall.model.OrderItem;
import com.grace.bootmall.model.Product;
import com.grace.bootmall.model.User;
import com.grace.bootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Order> getOrders(OrderRequestParam orderRequestParam) {

        List<Order> orderList = orderDao.getOrders(orderRequestParam);
        for (Order order : orderList) {
           List<OrderItem> orderItemList =  orderDao.getOrderItemByOrderId(order.getOrderId());
           order.setOrderItems(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderRequestParam orderRequestParam) {
        return orderDao.countOrder(orderRequestParam);
    }


    @Override
    @Transactional
    public Integer createOrder(Integer userId, OrderRequest orderRequest) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            log.warn("使用者 {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        List<BuyItem> buyItemList = orderRequest.getBuyItemList();
        for(BuyItem buyItem : buyItemList){
            OrderItem orderItem = new OrderItem();
            Product product = productDao.getProductById(buyItem.getProductId());
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存剩餘 {} 不足，需要 {} 數量", product.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            orderItem.setAmount(amount);
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setProductId(product.getProductId());
            orderItemList.add(orderItem);
        }
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderId);
            orderDao.createOrderItem(orderItem);
        }

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);
        order.setOrderItems(orderItemList);
        return order;
    }
}
