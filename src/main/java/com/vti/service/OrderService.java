package com.vti.service;

import com.vti.dto.OrderDetail;
import com.vti.dto.OrderDetailAdmin;
import com.vti.dto.OrderProductDto;
import com.vti.entity.*;
import com.vti.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public void placeOrder(Integer userId, Integer addressId, Integer paymentId) {
        User user = userRepository.findById(userId).orElse(null);
        Address address = addressRepository.findById(addressId).orElse(null);
        Payment payment = paymentRepository.findById(paymentId).orElse(null);

        if (user == null || address == null || payment == null) {
            return;
        }

        Cart cart = user.getCart();

        if (cart == null || cart.getCartProducts().isEmpty()) {
            return;
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setOrderStatus(payment.getType().equalsIgnoreCase("Ngân hàng") ? "Đã thanh toán" : "Chờ thanh toán");
        order.setOrderAddress(address.getAddress());
        order.setPaymentMethod(payment.getType());
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            Size size = cartProduct.getSize();

            if (product == null || size == null) {
                continue;
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setSize(size);
            orderProduct.setQuantity(cartProduct.getQuantity());
            orderProducts.add(orderProduct);
        }

        order.setOrderProducts(orderProducts);

        cart.getCartProducts().clear();

        orderRepository.save(order);
        cartRepository.save(cart);
    }



    @Override
    public OrderDetail getOrderDetailById(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(order.getId());
        orderDetail.setOrderStatus(order.getOrderStatus());
        orderDetail.setOrderDate(order.getOrderDate());
        orderDetail.setOrderAddress(order.getOrderAddress());
        orderDetail.setPaymentMethod(order.getPaymentMethod());

        List<OrderProductDto> orderProductDtos = new ArrayList<>();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Product product = orderProduct.getProduct();
            Size size = orderProduct.getSize();
            int quantity = orderProduct.getQuantity();
            String productName = product.getTitle();
            String productSize = String.valueOf(size.getType());
            double productTotal = product.getPrice() * quantity;
            String productImage = product.getImage();

            OrderProductDto orderProductDto = new OrderProductDto();
            orderProductDto.setProductName(productName);
            orderProductDto.setProductSize(productSize);
            orderProductDto.setQuantity(quantity);
            orderProductDto.setProductTotal(productTotal);
            orderProductDto.setProductImage(productImage);

            orderProductDtos.add(orderProductDto);
        }
        orderDetail.setOrderProductDtos(orderProductDtos);

        return orderDetail;
    }

    @Override
    public List<OrderDetail> getAllOrderDetailsByUserId(Integer userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return orders.stream()
                .map(order -> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(order.getId());
                    orderDetail.setOrderStatus(order.getOrderStatus());
                    orderDetail.setOrderDate(order.getOrderDate());
                    orderDetail.setOrderAddress(order.getOrderAddress());
                    orderDetail.setPaymentMethod(order.getPaymentMethod());
                    List<OrderProductDto> orderProductDtos = order.getOrderProducts().stream()
                            .map(orderProduct -> {
                                Product product = orderProduct.getProduct();
                                Size size = orderProduct.getSize();
                                int quantity = orderProduct.getQuantity();
                                String productName = product.getTitle();
                                String productSize = String.valueOf(size.getType());
                                double productTotal = product.getPrice() * quantity;
                                String productImage = product.getImage();

                                OrderProductDto orderProductDto = new OrderProductDto();
                                orderProductDto.setProductName(productName);
                                orderProductDto.setProductSize(productSize);
                                orderProductDto.setQuantity(quantity);
                                orderProductDto.setProductTotal(productTotal);
                                orderProductDto.setProductImage(productImage);

                                return orderProductDto;
                            })
                            .collect(Collectors.toList());

                    orderDetail.setOrderProductDtos(orderProductDtos);

                    return orderDetail;
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<OrderDetailAdmin> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> {
                    OrderDetailAdmin orderDetailAdmin = new OrderDetailAdmin();
                    orderDetailAdmin.setId(order.getId());
                    orderDetailAdmin.setOrderStatus(order.getOrderStatus());
                    orderDetailAdmin.setOrderDate(order.getOrderDate());
                    orderDetailAdmin.setOrderAddress(order.getOrderAddress());
                    orderDetailAdmin.setPaymentMethod(order.getPaymentMethod());
                    orderDetailAdmin.setUserName(order.getUser().getUsername());
                    orderDetailAdmin.setUserPhoneNumber(order.getUser().getPhoneNumber());

                    List<OrderProductDto> orderProductDtos = order.getOrderProducts().stream()
                            .map(orderProduct -> {
                                Product product = orderProduct.getProduct();
                                Size size = orderProduct.getSize();
                                int quantity = orderProduct.getQuantity();
                                String productName = product.getTitle();
                                String productSize = String.valueOf(size.getType());
                                double productTotal = product.getPrice() * quantity;
                                String productImage = product.getImage();

                                OrderProductDto orderProductDto = new OrderProductDto();
                                orderProductDto.setProductName(productName);
                                orderProductDto.setProductSize(productSize);
                                orderProductDto.setQuantity(quantity);
                                orderProductDto.setProductTotal(productTotal);
                                orderProductDto.setProductImage(productImage);

                                return orderProductDto;
                            })
                            .collect(Collectors.toList());

                    orderDetailAdmin.setOrderProductDtos(orderProductDtos);

                    return orderDetailAdmin;
                })
                .collect(Collectors.toList());
    }
}


