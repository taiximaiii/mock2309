package com.vti.service;

import com.vti.entity.Category;
import com.vti.entity.Product;
import com.vti.entity.Size;
import com.vti.form.ProductFilterForm;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
   public List<Product> getAllProducts(String search, ProductFilterForm filter);
   public Product getProductByID(int id);
   public void updateProduct(int id, ProductFormForUpdating form);

   public void deleteProduct(int id);
   Product addProduct(ProductFormForCreating productForm, MultipartFile imageFile);



}
