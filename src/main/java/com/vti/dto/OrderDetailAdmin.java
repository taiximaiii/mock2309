package com.vti.dto;

import com.vti.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class OrderDetailAdmin {

        private Integer id;
        private String orderStatus;
        private Date orderDate;
        private String orderAddress;
        private String paymentMethod;
        private String userName;
        private String userPhoneNumber;
        private List<OrderProductDto> orderProductDtos;

}
