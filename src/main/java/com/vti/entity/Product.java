package com.vti.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title", length = 250,nullable = false)
    private String title;

    @Column(name="price",nullable = false)
    private int price;

    @Column(name="`description`", length = 500,nullable = false)
    private String description;

    @Column(name="image", length = 500,nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    @JsonBackReference
    private Category category;

    @ManyToMany(mappedBy = "products",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Size> sizes;

    @Column(name = "createdate")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<CartProduct> cartProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<OrderProduct> orderProducts = new ArrayList<>();

}
