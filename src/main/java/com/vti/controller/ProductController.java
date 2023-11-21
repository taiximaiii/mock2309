package com.vti.controller;
import com.vti.dto.ProductDTO;
import com.vti.entity.Product;
import com.vti.form.ProductFilterForm;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;
import com.vti.service.IProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
@CrossOrigin("*")

public class ProductController {

    @Autowired
    private IProductService service;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/all")
    public List<ProductDTO> getAllProducts(
            @RequestParam(value = "search", required = false) String search,
            ProductFilterForm filter
    ) {
        List<Product> entityList = service.getAllProducts(search, filter);
        List<ProductDTO> dtos = modelMapper.map(entityList, new TypeToken<List<ProductDTO>>() {}.getType());
        return dtos;
    }

    @GetMapping(value = "detail/{id}")
    public ProductDTO findById(@PathVariable("id")int id){
        Product product = service.getProductByID(id);
        ProductDTO dto = modelMapper.map(product,ProductDTO.class);
        return dto;

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable(name = "id") int id,
            @RequestBody ProductFormForUpdating form) {
        service.updateProduct(id,form);
        return ResponseEntity.ok("Update product successfully");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") int id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Delete product successfully");
    }


    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(
            @RequestParam("title") String title,
            @RequestParam("price") String price,
            @RequestParam("description") String description,
            @RequestParam("categoryId") int categoryId,
            @RequestParam("sizeIds") List<Integer> sizeIds,
            @RequestParam(value = "file") MultipartFile file
    ) {
        ProductFormForCreating productForm = new ProductFormForCreating();
        productForm.setTitle(title);
        productForm.setPrice(price);
        productForm.setDescription(description);
        productForm.setCategoryId(categoryId);
        productForm.setSizeIds(sizeIds);

        Product createdProduct = service.addProduct(productForm, file);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

}
