package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.dao.UserDao;
import com.beaconfire.quizApp.domain.User;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserDao userDao;

    public RegisterServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(User user) {
        if (userDao.emailExists(user.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        userDao.insertUser(user);
    }
}
