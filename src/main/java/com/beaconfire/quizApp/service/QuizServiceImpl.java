package com.beaconfire.quizApp.service;

import com.beaconfire.quizApp.dao.QuizDao;
import com.beaconfire.quizApp.dao.QuizQuestionDao;
import com.beaconfire.quizApp.domain.Quiz;
import com.beaconfire.quizApp.domain.Question;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizDao quizDao;
    private final QuizQuestionDao quizQuestionDao;

    public QuizServiceImpl(QuizDao quizDao, QuizQuestionDao quizQuestionDao) {
        this.quizDao = quizDao;
        this.quizQuestionDao = quizQuestionDao;
    }

    @Override
    public void updateUserChoice(int quizId, int questionId, int choiceId) {
        quizQuestionDao.updateUserChoice(quizId, questionId, choiceId);
    }

    @Override
    public Quiz getOngoingQuizByUser(int userId) {
        return quizDao.findOngoingQuizByUser(userId);
    }

    @Override
    public Quiz createQuiz(Quiz quiz) {
        // insert quiz table
        quiz.setName("Quiz - " + LocalDateTime.now().withNano(0).toString());
        int quizId = quizDao.createQuiz(quiz);
        quiz.setQuizId(quizId);

        // Step 2: get quesiton random
        List<Question> randomQuestions = getRandomQuestions(quiz.getCategoryId(), 3);

        // insert into quiz question table
        for (Question question : randomQuestions) {
            insertQuizQuestion(quizId, question.getQuestionId());
        }

        return quiz;
    }

    @Override
    public void insertQuizQuestion(int quizId, int questionId) {
        quizQuestionDao.insertQuizQuestion(quizId, questionId);
    }



    @Override
    public void setQuizEndTime(int quizId, LocalDateTime endTime) {
        quizDao.updateQuizEndTime(quizId, Timestamp.valueOf(endTime));
    }


    @Override
    public Quiz getQuizById(int quizId) {
        return quizDao.findQuizById(quizId);
    }

    @Override
    public List<Question> getRandomQuestions(int categoryId, int count) {
        return quizDao.findQuestionsByCategory(categoryId, count);
    }

    @Override
    public List<Question> getQuestionsByQuizId(int quizId) {
        return quizDao.findQuestionsByQuizId(quizId);
    }
    @Override
    public int getCorrectAnswerCount(int quizId) {
        return quizQuestionDao.countCorrectAnswersByQuizId(quizId);
    }
    @Override
    public List<Quiz> getCompletedQuizzesByUser(int userId) {
        return quizDao.findCompletedQuizzesByUser(userId);
    }
    @Override
    public Quiz getOngoingQuizByUserAndCategory(int userId, int categoryId) {
        return quizDao.findOngoingQuizByUserAndCategory(userId, categoryId);
    }



}
