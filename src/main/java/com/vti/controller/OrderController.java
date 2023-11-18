package com.vti.controller;

import com.vti.security.UserPrincipal;
import com.vti.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestParam("addressId") Integer addressId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestParam("paymentId") Integer paymentId){
        orderService.placeOrder(userPrincipal.getId(), addressId,paymentId);
        return ResponseEntity.ok("Order Success");
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllOrderForUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return new ResponseEntity<>(orderService.getAllOrderDetailsByUserId(userPrincipal.getId()),HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getOrderDetail(@RequestParam("orderId") Integer orderId){
        return new ResponseEntity<>(orderService.getOrderDetailById(orderId),HttpStatus.OK);
    }
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllOrderForAdmin(){
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK);
    }
}
