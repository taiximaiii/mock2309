package com.vti.dto;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class  OrderDetail {
    private Integer id;
    private String orderStatus;
    private Date orderDate;
    private String orderAddress;
    private String paymentMethod;
    private List<OrderProductDto> orderProductDtos;
}
