// 18)
package com.OrderService.order.controller;

import com.OrderService.order.dto.OrderRequest;
import com.OrderService.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String placeOrder (@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order placed successfully";
    }

}
