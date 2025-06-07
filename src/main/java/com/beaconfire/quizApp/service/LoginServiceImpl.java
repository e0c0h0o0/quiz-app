package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.dao.UserDao;
import com.beaconfire.quizApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserDao userDao;

    @Autowired
    public LoginServiceImpl(UserDao userDao) {

        this.userDao = userDao;
    }

    @Override
    public Optional<User> validateLogin(String email, String password) {
        User user = userDao.findByEmailAndPassword(email, password);
        return Optional.ofNullable(user);
    }

}

