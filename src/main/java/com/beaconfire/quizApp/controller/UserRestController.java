package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.dao.UserDao;
import com.beaconfire.quizApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserRestController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (userDao.emailExists(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        userDao.insertUser(user);
        return ResponseEntity.ok("User created");
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam int userId) {
        userDao.deleteUserById(userId);
        return ResponseEntity.ok("User deleted");
    }

    @PatchMapping("/user/{userId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable int userId, @RequestParam boolean activate) {
        userDao.updateUserStatus(userId, activate);
        return ResponseEntity.ok("User status updated");
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) Integer userId) {
        if (userId == null) {
            return ResponseEntity.ok(userDao.getAllUsers());
        } else {
            return ResponseEntity.ok(userDao.findUserById(userId));
        }
    }
}
