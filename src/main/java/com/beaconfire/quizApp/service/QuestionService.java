package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();
    Question getQuestionById(int id);
    void updateQuestionWithChoices(Question question);
    void addQuestionWithChoices(Question question);
    void toggleQuestionStatus(int id, boolean active);
}

