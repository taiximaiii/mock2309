package com.vti.form;

import com.vti.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFormForCreating {

    private String title;
    private String price;
    private String description;
    private String image;
    private int categoryId;
    private List<Integer> sizeIds;
}
