package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.dao.QuizDao;
import com.beaconfire.quizApp.domain.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizRestController {

    private final QuizDao quizDao;

    public QuizRestController(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @GetMapping("/quiz")
    public ResponseEntity<List<Quiz>> getCompletedQuizzesByUser(@RequestParam int userId) {
        System.out.println(">>> Received userId: " + userId);
        List<Quiz> quizzes = quizDao.findCompletedQuizzesByUser(userId);
        return ResponseEntity.ok(quizzes);
    }
}

