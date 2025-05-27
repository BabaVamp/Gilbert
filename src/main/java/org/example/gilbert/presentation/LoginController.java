package org.example.gilbert.presentation;

import jakarta.servlet.http.HttpSession;
import org.example.gilbert.application.Userservice;
import org.example.gilbert.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final Userservice userservice;

    public LoginController(Userservice userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/")
    public String root() {
        return "home";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user, Model model) {
        try {
            userservice.createUser(user);
            model.addAttribute("success", "Account created successfully! Please sign in.");
            return "signin";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "signup";
        }
    }


    @GetMapping("/signin")
    public String showSigninForm(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String processSignin(@ModelAttribute User user, Model model, HttpSession session) {
        User authenticatedUser = userservice.signIn(user.getEmail(), user.getPassword());

        if (authenticatedUser != null) {
            // User is valid - store in session
            session.setAttribute("loggedInUser", authenticatedUser);
            session.setAttribute("isAuthenticated", true);
            session.setAttribute("userID", authenticatedUser.getMemberID());
            return "redirect:/home";
        } else {
            // Invalid credentials
            model.addAttribute("error", "Invalid email or password");
            model.addAttribute("user", user);
            return "signin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/signin";
        }

        model.addAttribute("user", loggedInUser);
        return "profile";
    }
}