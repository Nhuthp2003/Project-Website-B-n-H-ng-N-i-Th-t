package com.arphor.service.impi;

import com.arphor.entity.Category;
import com.arphor.dao.CategoryDAO;
import com.arphor.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public Category getCategoryById(int categoryId) {
        return categoryDAO.findById(categoryId).orElseThrow();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryDAO.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryDAO.save(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
    	categoryDAO.deleteById(categoryId);
    }
}