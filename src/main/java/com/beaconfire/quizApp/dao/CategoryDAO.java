package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> findAllCategories();
}
