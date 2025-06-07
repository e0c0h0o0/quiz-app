package com.beaconfire.quizApp.service;


import com.beaconfire.quizApp.dao.UserDao;
import com.beaconfire.quizApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminServiceImpl(UserDao userDao, JdbcTemplate jdbcTemplate) {
        this.userDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getTotalUserCount() {
        return userDao.getTotalUserCount();
    }

    @Override
    public List<User> getPagedUsers(int page, int size) {
        int offset = (page - 1) * size;
        return userDao.getUsersByPage(offset, size);
    }

    @Override
    public void updateUserStatus(int userId, boolean isActive) {
        userDao.updateUserStatus(userId, isActive);
    }

    @Override
    public String getMostPopularCategory() {
        String sql = "SELECT c.name FROM quiz q " +
                "JOIN category c ON q.category_id = c.category_id " +
                "GROUP BY q.category_id " +
                "ORDER BY COUNT(*) DESC " +
                "LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            return "N/A";
        }
    }
}



