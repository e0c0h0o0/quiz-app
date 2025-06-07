package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAllCategories() {
        String sql = "SELECT category_id, name FROM Category";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setName(rs.getString("name"));
            return category;
        });
    }
}
