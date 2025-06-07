package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Choice;
import com.beaconfire.quizApp.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAllWithChoices();
    Question findById(int questionId);
    void updateQuestion(Question question);
    void updateChoices(List<Choice> choices);
    void addQuestion(Question question, List<Choice> choices);
    void setActiveStatus(int questionId, boolean isActive);
}


