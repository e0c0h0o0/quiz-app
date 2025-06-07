package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.domain.User;
import com.beaconfire.quizApp.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/home")
    public String adminHome(Model model, HttpSession session) {
        // redirect
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/home";
        }

        // count
        int userCount = adminService.getTotalUserCount();
        String popularCategory = adminService.getMostPopularCategory();

        model.addAttribute("userCount", userCount);
        model.addAttribute("popularCategory", popularCategory);
        return "adminHome";
    }

    @GetMapping("/users")
    public String showUserPage(@RequestParam(defaultValue = "1") int page, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/home";
        }

        int size = 5;
        List<User> users = adminService.getPagedUsers(page, size);
        int totalUsers = adminService.getTotalUserCount();
        int totalPages = (int) Math.ceil((double) totalUsers / size);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "adminUser";
    }

    @PostMapping("/users/status")
    public String changeUserStatus(@RequestParam int userId, @RequestParam boolean isActive, @RequestParam int page, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/home";
        }

        adminService.updateUserStatus(userId, !isActive);
        return "redirect:/admin/users?page=" + page;
    }

}

