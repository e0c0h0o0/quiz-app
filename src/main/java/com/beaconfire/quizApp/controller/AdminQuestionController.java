package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.dao.CategoryDAO;
import com.beaconfire.quizApp.domain.Choice;
import com.beaconfire.quizApp.domain.Question;
import com.beaconfire.quizApp.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/questions")
public class AdminQuestionController {

    private final QuestionService questionService;
    private final CategoryDAO categoryDAO;

    public AdminQuestionController(QuestionService questionService, CategoryDAO categoryDAO) {
        this.questionService = questionService;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public String showQuestions(Model model) {
        List<Question> questions = questionService.getAllQuestions();
        model.addAttribute("questionList", questions);

        Map<Integer, String> categoryMap = new HashMap<>();
        categoryDAO.findAllCategories().forEach(c -> categoryMap.put(c.getCategoryId(), c.getName()));
        model.addAttribute("categoryMap", categoryMap);

        return "adminQuestion";
    }


    @GetMapping("/edit")
    public String editPage(@RequestParam int id, Model model) {
        model.addAttribute("question", questionService.getQuestionById(id));
        model.addAttribute("categoryList", categoryDAO.findAllCategories());
        return "editQuestion";
    }

    @PostMapping("/edit")
    public String editQuestion(@ModelAttribute Question question, @RequestParam("correct") int correctIndex) {
        List<Choice> choices = question.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            choices.get(i).setCorrect(i == correctIndex);
        }
        question.setChoices(choices);
        questionService.updateQuestionWithChoices(question);
        return "redirect:/admin/questions";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("categoryList", categoryDAO.findAllCategories());
        return "addQuestion";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute Question question, @RequestParam("correct") int correctIndex) {
        List<Choice> choices = question.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            choices.get(i).setCorrect(i == correctIndex);
        }
        question.setChoices(choices);
        questionService.addQuestionWithChoices(question);
        return "redirect:/admin/questions";
    }

    @PostMapping("/toggle")
    public String toggle(@RequestParam int id, @RequestParam String active) {
        boolean isActive = Boolean.parseBoolean(active);
        questionService.toggleQuestionStatus(id, isActive);
        return "redirect:/admin/questions";
    }

}
