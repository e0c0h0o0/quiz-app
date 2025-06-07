package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.domain.Question;
import com.beaconfire.quizApp.domain.Quiz;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizService {
    Quiz getOngoingQuizByUser(int userId);
    Quiz createQuiz(Quiz quiz);
    void insertQuizQuestion(int quizId, int questionId);
    void updateUserChoice(int quizId, int questionId, int choiceId);
    void setQuizEndTime(int quizId, LocalDateTime endTime);
    Quiz getQuizById(int quizId);
    List<Question> getRandomQuestions(int categoryId, int count);
    List<Question> getQuestionsByQuizId(int quizId);

    int getCorrectAnswerCount(int quizId);

    List<Quiz> getCompletedQuizzesByUser(int userId);
    Quiz getOngoingQuizByUserAndCategory(int userId, int categoryId);

}

