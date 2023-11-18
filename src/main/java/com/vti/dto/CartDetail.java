package com.vti.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    private String productName;
    private String productSize;
    private int quantity;
    private double productTotal;
    private String productImage;
}
