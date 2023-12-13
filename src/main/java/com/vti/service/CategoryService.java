package com.vti.service;

import com.vti.entity.Category;
import com.vti.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    @Override
    public void addCategory(Category category){
        categoryRepository.save(category);
    }
}
