package com.vti.dto;

import lombok.Data;

@Data
public class OrderProductDto {
    private String productName;
    private String productSize;
    private int quantity;
    private double productTotal;
    private String productImage;
}
