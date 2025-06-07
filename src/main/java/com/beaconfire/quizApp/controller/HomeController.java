package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.domain.Category;

import com.beaconfire.quizApp.domain.User;
import com.beaconfire.quizApp.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;



@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        if (user.isAdmin()) {
            return "redirect:/admin/home";
        }

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "home";
    }



}
