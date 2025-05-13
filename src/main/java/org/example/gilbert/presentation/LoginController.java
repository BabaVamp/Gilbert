package org.example.gilbert.presentation;

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
    public String processSignup(@ModelAttribute User user) {
        // Process user registration here
        // e.g., userService.register(user);

        return "redirect:/login"; // Redirect to login page after successful signup
    }



//    @PostMapping("/signup")
//    public String signup(@ModelAttribute User user, Model model) {
//        try{
//            User existingUser = userservice.GetUserByUsername(user.getUsername());
//            if(existingUser != null) {
//                model.addAttribute("user", existingUser);
//                return "signup";
//            }
//            userservice.createUser(user);
//            return "redirect:/signin";
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage());
//            return "signup";
//        }
//    }

    @GetMapping("/signin")
    public String showSigninform(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute User user, Model model) {

        return "home";
    }






}
