package com.grace.bootmall.controller;

import com.grace.bootmall.dto.OrderRequest;
import com.grace.bootmall.dto.OrderRequestParam;
import com.grace.bootmall.model.Order;
import com.grace.bootmall.service.OrderService;
import com.grace.bootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
                                                 @RequestParam(defaultValue = "0")  @Min(0) Integer offset) {

        OrderRequestParam orderRequestParam = new OrderRequestParam();
        if(orderRequestParam.getUserId() != null) {
            orderRequestParam.setUserId(userId);
        }
        orderRequestParam.setLimit(limit);
        orderRequestParam.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderRequestParam);
        Integer count = orderService.countOrder(orderRequestParam);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/users/{userId}/order")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                             @RequestBody @Valid OrderRequest orderRequest){
        Integer orderId = orderService.createOrder(userId, orderRequest);
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
