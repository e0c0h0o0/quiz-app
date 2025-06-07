package com.beaconfire.quizApp.controller;

import com.beaconfire.quizApp.domain.User;
import com.beaconfire.quizApp.service.LoginService;
import com.beaconfire.quizApp.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @Autowired
    public LoginController(LoginService loginService, RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        // 已登录用户访问 /login，则根据身份重定向
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            return user.isAdmin() ? "redirect:/admin/home" : "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("email") String email,
                            @RequestParam String password,
                            HttpServletRequest request,
                            Model model) {
        Optional<User> possibleUser = loginService.validateLogin(email, password);
        System.out.println("possibleUser = " + possibleUser);
        if (possibleUser.isPresent()) {
            User user = possibleUser.get();

            if (!user.isActive()) {
                model.addAttribute("errorMessage", "Invalid username or password.");

                return "login";
            }

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) oldSession.invalidate();

            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", user);
            newSession.setAttribute("isAdmin", user.isAdmin());

            return user.isAdmin() ? "redirect:/admin/home" : "redirect:/home";
        } else {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        try {
            user.setActive(true);

            registerService.registerUser(user);
            model.addAttribute("message", "Registration successful! Please login.");
            return "login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register"; // 回到注册页面并显示错误信息
        }
    }


}
