package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.domain.Question;
import com.beaconfire.quizApp.domain.Quiz;
import com.beaconfire.quizApp.domain.User;
import com.beaconfire.quizApp.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/quiz")
public class QuizController {

    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/start")
    public String startQuiz(@RequestParam("categoryId") int categoryId,
                            HttpSession session,
                            Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login"; // 未登录用户重定向
        }

        int userId = currentUser.getUserId();

//        Quiz existingQuiz = quizService.getOngoingQuizByUserAndCategory(userId, categoryId);
//        if (existingQuiz != null) {
//            return "redirect:/quiz/take?quizId=" + existingQuiz.getQuizId();
//        }


        // 构造并创建新的 Quiz
        Quiz newQuiz = new Quiz();
        newQuiz.setUserId(userId);
        newQuiz.setCategoryId(categoryId);
        newQuiz.setTimeStart(LocalDateTime.now());

        Quiz createdQuiz = quizService.createQuiz(newQuiz);


        return "redirect:/quiz/take?quizId=" + createdQuiz.getQuizId();
    }

    @GetMapping("/take")
    public String takeQuiz(@RequestParam("quizId") int quizId,
                           Model model) {
        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = quizService.getQuestionsByQuizId(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitQuiz(@RequestParam Map<String, String> params) {
        int quizId = Integer.parseInt(params.get("quizId"));

        for (String key : params.keySet()) {
            if (key.startsWith("question_")) {
                int questionId = Integer.parseInt(key.split("_")[1]);
                int choiceId = Integer.parseInt(params.get(key));
                quizService.updateUserChoice(quizId, questionId, choiceId);
            }
        }

        // 设置 quiz 结束时间为现在
        quizService.setQuizEndTime(quizId, LocalDateTime.now());

        return "redirect:/quiz/result?quizId=" + quizId;
    }
    @GetMapping("/result")
    public String showResult(@RequestParam("quizId") int quizId, Model model, HttpSession session) {
        Quiz quiz = quizService.getQuizById(quizId);
        User user = (User) session.getAttribute("user");

        // 非管理员不能查看别人的 quiz
        if (user == null || (!user.isAdmin() && user.getUserId() != quiz.getUserId())) {
            return "redirect:/home";
        }

        List<Question> questions = quizService.getQuestionsByQuizId(quizId);
        int correctCount = quizService.getCorrectAnswerCount(quizId);
        boolean passed = correctCount >= 2;

        quiz.setUserFullName(user.getFirstname() + " " + user.getLastname());

        model.addAttribute("user", user);
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("passed", passed);
        model.addAttribute("questionDetails", questions);

        return "quizResult";
    }
    @GetMapping("/history")
    public String showUserQuizHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.isAdmin()) {
            return "redirect:/login"; // 非法访问拦截
        }

        List<Quiz> quizList = quizService.getCompletedQuizzesByUser(user.getUserId());

        model.addAttribute("quizList", quizList);
        return "quizHistory";
    }



}


