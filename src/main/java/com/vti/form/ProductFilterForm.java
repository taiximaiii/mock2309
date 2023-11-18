package com.vti.form;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ProductFilterForm {
    private Integer minPrice;
    private Integer maxPrice;
}
