package com.projectsecuirty.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projectsecuirty.security.entity.AppUser;
import com.projectsecuirty.security.service.UserService;


@Controller
public class MainController {

    @Autowired private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";   // src/main/resources/templates/login.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";  // register.html
    }

    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") AppUser user) {
        userService.saveUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/home")
    public String home() {
        return "home";    // secured landing page
    }
}
	