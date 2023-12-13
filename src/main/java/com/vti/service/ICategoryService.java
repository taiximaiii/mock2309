package com.vti.service;

import com.vti.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategory();
    void addCategory(Category category);
}
