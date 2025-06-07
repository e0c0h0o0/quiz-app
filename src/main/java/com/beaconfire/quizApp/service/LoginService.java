package com.beaconfire.quizApp.service;



import com.beaconfire.quizApp.domain.User;

import java.util.Optional;

public interface LoginService {
    Optional<User> validateLogin(String email, String password);
}