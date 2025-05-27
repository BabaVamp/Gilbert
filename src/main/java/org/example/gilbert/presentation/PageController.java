package org.example.gilbert.presentation;

import jakarta.servlet.http.HttpSession;
import org.example.gilbert.application.ListingService;
import org.example.gilbert.domain.Listing;
import org.example.gilbert.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {


    private final ListingService listingService;

    public PageController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/brands")
    public String brands() {
        return "brands"; // This will look for brands.html in your templates folder
    }

    @GetMapping("/home")
    public String home(Model model, @RequestParam(required = false) String brand,
                       @RequestParam(required = false) String category) {
        List<Listing> listings;

        if (brand != null && !brand.isEmpty()) {
            listings = listingService.getListingsByBrand(brand);
            model.addAttribute("activeFilter", "Brand: " + brand);
        } else if (category != null && !category.isEmpty()) {
            listings = listingService.getListingsByCategory(category);
            model.addAttribute("activeFilter", "Category: " + category);
        } else {
            listings = listingService.getAllActiveListings();
        }

        model.addAttribute("listings", listings);
        return "home";
    }

    @GetMapping("/fav")
    public String favorites(HttpSession session, Model model) {
        if (session.getAttribute("isAuthenticated") == null) {
            return "fav"; // Will show login required message
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", loggedInUser);
        return "fav";
    }

    @GetMapping("/favs")
    public String favs(HttpSession session, Model model) {
        return favorites(session, model); // Redirect to fav for consistency
    }
}
