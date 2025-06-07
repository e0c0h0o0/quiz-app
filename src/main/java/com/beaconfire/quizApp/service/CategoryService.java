package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.dao.CategoryDAO;
import com.beaconfire.quizApp.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<Category> getAllCategories() {
        return categoryDAO.findAllCategories();
    }
}
