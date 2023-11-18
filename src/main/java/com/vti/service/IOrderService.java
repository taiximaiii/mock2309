package com.vti.service;

import com.vti.dto.OrderDetail;
import com.vti.dto.OrderDetailAdmin;
import com.vti.entity.Order;

import java.util.List;

public interface IOrderService {
    void placeOrder(Integer userId, Integer addressId, Integer paymentId);
    OrderDetail getOrderDetailById(Integer orderId);
    List<OrderDetail> getAllOrderDetailsByUserId(Integer userId);
    List<OrderDetailAdmin> getAllOrders();

}
