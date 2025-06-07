package com.beaconfire.quizApp.dao;

import com.beaconfire.quizApp.domain.Choice;
import com.beaconfire.quizApp.domain.Question;
import com.beaconfire.quizApp.domain.Quiz;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class QuizDaoImpl implements QuizDao {
    private final JdbcTemplate jdbc;

    public QuizDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Quiz findOngoingQuizByUser(int userId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ? AND is_completed = FALSE LIMIT 1";
        List<Quiz> list = jdbc.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Integer createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start, is_completed) VALUES (?, ?, ?, ?, FALSE)";
        jdbc.update(sql, quiz.getUserId(), quiz.getCategoryId(), quiz.getName(), Timestamp.valueOf(quiz.getTimeStart()));
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }



    @Override
    public void updateQuizEndTime(int quizId, Timestamp endTime) {
        String sql = "UPDATE Quiz SET time_end = ?, is_completed = TRUE WHERE quiz_id = ?";
        jdbc.update(sql, endTime, quizId);
    }


    @Override
    public Quiz findQuizById(int quizId) {
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(Quiz.class), quizId);
    }

    @Override
    public List<Question> findQuestionsByCategory(int categoryId, int count) {
        String sql = "SELECT * FROM Question WHERE category_id = ? AND is_active = TRUE ORDER BY RAND() LIMIT ?";
        List<Question> questions = jdbc.query(sql, new BeanPropertyRowMapper<>(Question.class), categoryId, count);
        for (Question q : questions) {
            q.setChoices(findChoicesByQuestionId(q.getQuestionId()));
        }
        return questions;
    }

    @Override
    public List<Question> findQuestionsByQuizId(int quizId) {
        String sql = "SELECT q.* FROM Question q JOIN QuizQuestion qq ON q.question_id = qq.question_id WHERE qq.quiz_id = ?";
        List<Question> questions = jdbc.query(sql, new BeanPropertyRowMapper<>(Question.class), quizId);
        for (Question q : questions) {
            q.setChoices(findChoicesByQuestionId(q.getQuestionId()));
        }
        return questions;
    }

    @Override
    public List<Choice> findChoicesByQuestionId(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<>(Choice.class), questionId);
    }

    /**
     *Query all “completed” quizzes from the Quiz table
     * and support paging, user filtering, category filtering, sorting.
     */
    @Override
    public List<Quiz> findAllWithFilters(Integer userId, Integer categoryId, String sortBy, String order, int offset, int limit) {
        StringBuilder sql = new StringBuilder(
                "SELECT q.*, u.firstname, u.lastname, c.name AS category_name " +
                        "FROM Quiz q " +
                        "JOIN User u ON q.user_id = u.user_id " +
                        "JOIN Category c ON q.category_id = c.category_id " +
                        "WHERE q.is_completed = TRUE "
        );
        // 参数列表
        List<Object> params = new java.util.ArrayList<>();

        if (userId != null) {
            sql.append("AND q.user_id = ? ");
            params.add(userId);
        }

        if (categoryId != null) {
            sql.append("AND q.category_id = ? ");
            params.add(categoryId);
        }

        // 排序字段判断
        switch (sortBy) {
            case "name":
                sql.append("ORDER BY u.firstname ").append(order).append(", u.lastname ").append(order).append(" ");
                break;
            case "category":
                sql.append("ORDER BY c.name ").append(order).append(" ");
                break;
            default:
                sql.append("ORDER BY q.time_start ").append(order).append(" ");
        }

        sql.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbc.query(sql.toString(), (rs, rowNum) -> {
            Quiz quiz = new Quiz();
            quiz.setQuizId(rs.getInt("quiz_id"));
            quiz.setUserId(rs.getInt("user_id"));
            quiz.setCategoryId(rs.getInt("category_id"));
            quiz.setName(rs.getString("name"));
            quiz.setTimeStart(rs.getTimestamp("time_start").toLocalDateTime());
            quiz.setTimeEnd(rs.getTimestamp("time_end") != null ? rs.getTimestamp("time_end").toLocalDateTime() : null);
            quiz.setCompleted(rs.getBoolean("is_completed"));

            // 附加信息：可设置 quiz.setUserName(), quiz.setCategoryName() 如果你在 Quiz 中加字段
            quiz.setUserFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
            quiz.setCategoryName(rs.getString("category_name"));
            return quiz;
        }, params.toArray());//will pass all ? parameters in order.
    }
    //Counts the total number of completed quizzes that meet certain filters (by user or category).
    @Override
    public int countAllWithFilters(Integer userId, Integer categoryId) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM Quiz q " +
                        "JOIN User u ON q.user_id = u.user_id " +
                        "JOIN Category c ON q.category_id = c.category_id " +
                        "WHERE q.is_completed = TRUE "
        );

        List<Object> params = new java.util.ArrayList<>();
        if (userId != null) {
            sql.append("AND q.user_id = ? ");
            params.add(userId);
        }

        if (categoryId != null) {
            sql.append("AND q.category_id = ? ");
            params.add(categoryId);
        }

        return jdbc.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }
    //record the user choice and question
    @Override
    public Integer findUserChoiceIdByQuizIdAndQuestionId(int quizId, int questionId) {
        String sql = "SELECT user_choice_id FROM QuizQuestion WHERE quiz_id = ? AND question_id = ?";
        try {
            return jdbc.queryForObject(sql, Integer.class, quizId, questionId);
        } catch (Exception e) {
            return null; // 如果没有找到记录，则返回 null
        }
    }

    @Override
    public List<Quiz> findCompletedQuizzesByUser(int userId) {
        String sql = "SELECT q.*, u.firstname, u.lastname, c.name AS category_name " +
                "FROM Quiz q " +
                "JOIN User u ON q.user_id = u.user_id " +
                "JOIN Category c ON q.category_id = c.category_id " +
                "WHERE q.user_id = ? AND q.is_completed = TRUE " +
                "ORDER BY q.time_end DESC";

        return jdbc.query(sql, (rs, rowNum) -> {
            Quiz quiz = new Quiz();
            quiz.setQuizId(rs.getInt("quiz_id"));
            quiz.setUserId(rs.getInt("user_id"));
            quiz.setCategoryId(rs.getInt("category_id"));
            quiz.setName(rs.getString("name"));
            quiz.setTimeStart(rs.getTimestamp("time_start").toLocalDateTime());
            quiz.setTimeEnd(rs.getTimestamp("time_end").toLocalDateTime());
            quiz.setCompleted(true);
            quiz.setCategoryName(rs.getString("category_name"));
            quiz.setUserFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
            return quiz;
        }, userId);
    }
    @Override
    public Quiz findOngoingQuizByUserAndCategory(int userId, int categoryId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ? AND category_id = ? AND is_completed = FALSE LIMIT 1";
        List<Quiz> list = jdbc.query(sql, new BeanPropertyRowMapper<>(Quiz.class), userId, categoryId);
        return list.isEmpty() ? null : list.get(0);
    }
}
