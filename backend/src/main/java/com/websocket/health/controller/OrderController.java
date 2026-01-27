package com.websocket.health.controller;

import com.websocket.health.mapper.MyOrderMapper;
import com.websocket.health.pojo.Order;
import com.websocket.health.pojo.Result;
import com.websocket.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@CrossOrigin
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/order")
    public Result findAll(Integer userId)
    {

        List<Order> all = orderService.findAll(userId);
        Result success = Result.success(all);
        success.setTotal(all.size());
        return success;
    }
    @PostMapping("/order")
    public Result addOrder(@RequestBody Order order)
    {
        return Result.success(orderService.addOrder(order));
    }
    @PutMapping("/order")
    public Result updateOrder(@RequestBody Order order)
    {
        return Result.success(orderService.updateOrder(order));
    }
    @DeleteMapping("/order")
    public Result deleteOrder(Integer id)
    {
        return Result.success(orderService.deleteOrder(id));
    }
}
