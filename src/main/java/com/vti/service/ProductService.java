package com.vti.service;
import com.vti.entity.Category;
import com.vti.entity.Product;
import com.vti.entity.Size;
import com.vti.form.ProductFilterForm;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;
import com.vti.repository.ICategoryRepository;
import com.vti.repository.IProductRepository;
import com.vti.repository.ISizeRepository;
import com.vti.specification.ProductSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {


    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ISizeRepository sizeRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Page<Product> getAllProducts(Pageable pageable, String search, ProductFilterForm filter) {
        Specification<Product> where = ProductSpecification.buildWhere(search, filter);
        return productRepository.findAll(where,pageable);
    }

    @Override
    public Product getProductByID(int id) {
        return productRepository.findById(id).get();
    }
    
    @Override
    public void updateProduct(int id, ProductFormForUpdating form) {
        Product product = getProductByID(id);
        product.setPrice(form.getPrice());
        product.setDescription(form.getDescription());
        productRepository.save(product);

    }
    @Override
    public void deleteProduct(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            if (product.getSizes() != null && !product.getSizes().isEmpty()) {
                for (Size size : product.getSizes()) {
                    size.getProducts().remove(product);
                }
                product.getSizes().clear();
            }

            productRepository.deleteById(id);
        }
    }


    @Override
    public Product addProduct(ProductFormForCreating productForm, MultipartFile imageFile) {
        Product product = new Product();
        product.setTitle(productForm.getTitle());
        product.setPrice(Integer.parseInt(productForm.getPrice()));
        product.setDescription(productForm.getDescription());
        Category category = categoryRepository.findById(productForm.getCategoryId()).orElse(null);
        product.setCategory(category);
        List<Size> sizes = sizeRepository.findByIdIn(productForm.getSizeIds());
        product.setSizes(sizes);


        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            String filePath = "uploads/" + fileName;
            product.setImage(fileName);
            try {
                Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Size size : sizes) {
            size.getProducts().add(product);
        }
        return productRepository.save(product);
    }

}
