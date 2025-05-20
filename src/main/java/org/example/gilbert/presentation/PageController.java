package org.example.gilbert.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/brands")
    public String brands() {
        return "brands"; // This will look for brands.html in your templates folder
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/fav")
    public String favorites() {
        return "fav";
    }

    @GetMapping("/favs")
    public String favs() {
        return "fav"; // Redirect to fav for consistency
    }
}
