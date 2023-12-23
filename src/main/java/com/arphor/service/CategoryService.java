package com.arphor.service;

import com.arphor.entity.Category;

import java.util.List;

public interface CategoryService {

	List<Category> getAllCategories();

    Category getCategoryById(int categoryId);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(int categoryId);
}
