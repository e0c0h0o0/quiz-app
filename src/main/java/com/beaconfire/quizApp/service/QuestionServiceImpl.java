package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.dao.QuestionDao;
import com.beaconfire.quizApp.domain.Question;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAllWithChoices();
    }

    @Override
    public Question getQuestionById(int id) {
        return questionDao.findById(id);
    }

    @Override
    public void updateQuestionWithChoices(Question question) {
        questionDao.updateQuestion(question);
        questionDao.updateChoices(question.getChoices());
    }

    @Override
    public void addQuestionWithChoices(Question question) {
        questionDao.addQuestion(question, question.getChoices());
    }

    @Override
    public void toggleQuestionStatus(int id, boolean active) {

        questionDao.setActiveStatus(id, active);
    }
}

