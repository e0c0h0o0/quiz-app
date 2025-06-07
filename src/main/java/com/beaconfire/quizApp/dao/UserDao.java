package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.User;
import java.util.List;

public interface UserDao {
    User findByEmailAndPassword(String email, String password);
    void insertUser(User user);
    boolean emailExists(String email);
    User findUserById(int userId);
    List<User> getUsersByPage(int offset, int limit);
    int getTotalUserCount();
    void updateUserStatus(int userId, boolean isActive);

    void deleteUserById(int userId);

    Object getAllUsers();
}
