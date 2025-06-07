package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Choice;
import com.beaconfire.quizApp.domain.Question;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final JdbcTemplate jdbc;

    public QuestionDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Question> findAllWithChoices() {
        String sql = "SELECT * FROM Question";
        List<Question> questions = jdbc.query(sql, new BeanPropertyRowMapper<>(Question.class));
        for (Question q : questions) {
            q.setChoices(findChoicesByQuestionId(q.getQuestionId()));
        }
        return questions;
    }

    @Override
    public Question findById(int id) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        Question q = jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Question.class), id);
        q.setChoices(findChoicesByQuestionId(q.getQuestionId()));
        return q;
    }

    @Override
    public void updateQuestion(Question question) {
        String sql = "UPDATE Question SET description = ?, category_id = ? WHERE question_id = ?";
        jdbc.update(sql, question.getDescription(), question.getCategoryId(), question.getQuestionId());
    }

    @Override
    public void updateChoices(List<Choice> choices) {
        String sql = "UPDATE Choice SET description = ?, correct = ? WHERE choice_id = ?";
        for (Choice c : choices) {
            jdbc.update(sql, c.getDescription(), c.isCorrect(), c.getChoiceId());
        }
    }

    @Override
    public void setActiveStatus(int questionId, boolean isActive) {
        String sql = "UPDATE Question SET is_active = ? WHERE question_id = ?";
        jdbc.update(sql, isActive, questionId);
    }

    @Override
    public void addQuestion(Question question, List<Choice> choices) {
        String sql = "INSERT INTO Question (category_id, description, is_active) VALUES (?, ?, TRUE)";
        jdbc.update(sql, question.getCategoryId(), question.getDescription());

        Integer questionId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        String choiceSql = "INSERT INTO Choice (question_id, description, correct) VALUES (?, ?, ?)";
        for (Choice c : choices) {
            jdbc.update(choiceSql, questionId, c.getDescription(), c.isCorrect());
        }
    }

    private List<Choice> findChoicesByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Choice.class), questionId);
    }
}

