package com.vti.dto;
import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {

    private int id;
    private String title;
    private int price;
    private String description;
    private String image;
    private String categoryType;
    private List<SizeDTO> sizes;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDate;


}
