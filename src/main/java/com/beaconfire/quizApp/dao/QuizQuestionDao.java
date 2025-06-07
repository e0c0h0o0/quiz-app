package com.beaconfire.quizApp.dao;


public interface QuizQuestionDao {
    void insertQuizQuestion(int quizId, int questionId);
    void updateUserChoice(int quizId, int questionId, int choiceId);

    int countCorrectAnswersByQuizId(int quizId);
}
