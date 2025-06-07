package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Choice;
import com.beaconfire.quizApp.domain.Question;
import com.beaconfire.quizApp.domain.Quiz;
import java.sql.Timestamp;
import java.util.List;

public interface QuizDao {
    Quiz findOngoingQuizByUser(int userId);

    Integer createQuiz(Quiz quiz);

    Quiz findQuizById(int quizId);

    List<Question> findQuestionsByCategory(int categoryId, int count);

    List<Question> findQuestionsByQuizId(int quizId);

    List<Choice> findChoicesByQuestionId(int questionId);

    void updateQuizEndTime(int quizId, Timestamp endTime);

    List<Quiz> findAllWithFilters(Integer userId, Integer categoryId, String sortBy, String order, int offset, int limit);

    int countAllWithFilters(Integer userId, Integer categoryId);

    Integer findUserChoiceIdByQuizIdAndQuestionId(int quizId, int questionId);

    List<Quiz> findCompletedQuizzesByUser(int userId);

    Quiz findOngoingQuizByUserAndCategory(int userId, int categoryId);
}

