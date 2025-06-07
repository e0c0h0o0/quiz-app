package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.domain.User;

import java.util.List;

public interface AdminService {

    int getTotalUserCount();
    String getMostPopularCategory();
    List<User> getPagedUsers(int page, int size);
    void updateUserStatus(int userId, boolean isActive);
}

