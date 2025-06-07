package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT user_id AS userId, email, password, firstname, lastname, is_active AS active, is_admin AS admin FROM User WHERE email = ? AND password = ?";

        String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);

        List<User> users = jdbcTemplate.query(sql, new Object[]{email, password},
                new BeanPropertyRowMapper<>(User.class));

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void insertUser(User user) {
        String sql = "INSERT INTO User (email, password, firstname, lastname, is_active, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.isActive(), user.isAdmin());
    }
    @Override
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public List<User> getUsersByPage(int offset, int limit) {
        String sql = "SELECT user_id AS userId, email, password, firstname, lastname, is_active AS active, is_admin AS admin FROM User LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), limit, offset);
    }

    @Override
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM User";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void updateUserStatus(int userId, boolean isActive) {
        String sql = "UPDATE User SET is_active = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, isActive, userId);
    }

    @Override
    public User findUserById(int userId) {
        String sql = "SELECT user_id AS userId, email, password, firstname, lastname, is_active AS active, is_admin AS admin FROM User WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId);
        return users.isEmpty() ? null : users.get(0);
    }
    // UserDaoImpl.java
    @Override
    public void deleteUserById(int userId) {
        String sql = "DELETE FROM User WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    // UserDaoImpl.java
    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT user_id AS userId, email, password, firstname, lastname, is_active AS active, is_admin AS admin FROM User";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }


}
