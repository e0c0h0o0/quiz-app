package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.dao.CategoryDAO;
import com.beaconfire.quizApp.dao.QuizDao;
import com.beaconfire.quizApp.dao.QuizQuestionDao;
import com.beaconfire.quizApp.dao.UserDao;
import com.beaconfire.quizApp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin/quiz-results")
public class AdminQuizResultController {

    private final QuizDao quizDao;

    private final UserDao userDao;

    private final CategoryDAO categoryDAO;

    private final QuizQuestionDao quizQuestionDao;

    @Autowired
    public AdminQuizResultController(QuizDao quizDao, UserDao userDao, CategoryDAO categoryDAO, QuizQuestionDao quizQuestionDao) {
        this.quizDao = quizDao;
        this.userDao = userDao;
        this.categoryDAO = categoryDAO;
        this.quizQuestionDao = quizQuestionDao;
    }

    @GetMapping
    public String showQuizResults(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "time") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            Model model) {

        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        List<Quiz> quizList = quizDao.findAllWithFilters(userId, categoryId, sortBy, order, offset, pageSize);
        int totalCount = quizDao.countAllWithFilters(userId, categoryId);
        int totalPages = (int) Math.ceil(totalCount / (double) pageSize);

        model.addAttribute("quizList", quizList);
        List<User> users = userDao.getUsersByPage(0, 1000); // 取前1000个用户用于筛选
        List<Category> categories = categoryDAO.findAllCategories();

        model.addAttribute("users", users);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("selectedUserId", userId);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);

        return "adminQuizResult";
    }
    @GetMapping("/result")
    public String showQuizResult(@RequestParam("id") int quizId, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        Quiz quiz = quizDao.findQuizById(quizId);

        if (sessionUser == null || (!sessionUser.isAdmin() && sessionUser.getUserId() != quiz.getUserId())) {
            return "redirect:/home";
        }

        User user = userDao.findUserById(quiz.getUserId());
        int correctCount = quizQuestionDao.countCorrectAnswersByQuizId(quizId);

        boolean passed = correctCount >= 2;

        List<Question> questionList = quizDao.findQuestionsByQuizId(quizId);
        int totalQuestions = questionList.size();

        for (Question q : questionList) {
            List<Choice> choices = quizDao.findChoicesByQuestionId(q.getQuestionId());
            q.setChoices(choices);

            Integer userChoiceId = quizDao.findUserChoiceIdByQuizIdAndQuestionId(quizId, q.getQuestionId());
            q.setUserChoiceId(userChoiceId);
        }


        model.addAttribute("quiz", quiz);
        model.addAttribute("user", user);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("passed", passed);
        model.addAttribute("questionDetails", questionList);
        model.addAttribute("totalQuestions", totalQuestions);

        return "quizResult";
    }
}

