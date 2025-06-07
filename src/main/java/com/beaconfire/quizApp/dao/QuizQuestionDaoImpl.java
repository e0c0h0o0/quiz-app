package com.beaconfire.quizApp.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuizQuestionDaoImpl implements QuizQuestionDao {

    private final JdbcTemplate jdbcTemplate;

    public QuizQuestionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //bound quiz and the questions selected
    @Override
    public void insertQuizQuestion(int quizId, int questionId) {
        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, quizId, questionId);
    }

    @Override
    public void updateUserChoice(int quizId, int questionId, int choiceId) {
        String sql = "UPDATE QuizQuestion SET user_choice_id = ? WHERE quiz_id = ? AND question_id = ?";
        jdbcTemplate.update(sql, choiceId, quizId, questionId);
    }

    @Override
    public int countCorrectAnswersByQuizId(int quizId) {
        String sql = "SELECT COUNT(*) " +
                "FROM QuizQuestion qq " +
                "JOIN Choice c ON qq.user_choice_id = c.choice_id " +
                "WHERE qq.quiz_id = ? AND c.correct = true";

        return jdbcTemplate.queryForObject(sql, Integer.class, quizId);
    }

}
