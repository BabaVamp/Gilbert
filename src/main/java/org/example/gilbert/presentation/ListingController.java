package org.example.gilbert.presentation;

import jakarta.servlet.http.HttpSession;
import org.example.gilbert.application.ListingService;
import org.example.gilbert.domain.Listing;
import org.example.gilbert.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/sell")
    public String showSellForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/signin";
        }

        model.addAttribute("listing", new Listing());
        model.addAttribute("user", loggedInUser);
        return "sell";
    }

    @PostMapping("/sell")
    public String processSell(@ModelAttribute Listing listing, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/signin";
        }

        try {
            listing.setSellerID(loggedInUser.getMemberID());
            listingService.createListing(listing);
            model.addAttribute("success", "Your item has been listed successfully!");
            return "redirect:/my-listings";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listing", listing);
            model.addAttribute("user", loggedInUser);
            return "sell";
        }
    }

    @GetMapping("/my-listings")
    public String myListings(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/signin";
        }

        List<Listing> userListings = listingService.getListingsBySellerID(loggedInUser.getMemberID());
        model.addAttribute("listings", userListings);
        model.addAttribute("user", loggedInUser);
        return "my-listings";
    }

    @GetMapping("/listing/{id}")
    public String viewListing(@PathVariable int id, Model model, HttpSession session) {
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return "redirect:/home";
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        boolean isOwner = loggedInUser != null && listing.getSellerID() == loggedInUser.getMemberID();

        model.addAttribute("listing", listing);
        model.addAttribute("isOwner", isOwner);
        return "listing-detail";
    }

    @PostMapping("/listing/{id}/mark-sold")
    public String markAsSold(@PathVariable int id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/signin";
        }

        if (listingService.isOwner(id, loggedInUser.getMemberID())) {
            listingService.markAsSold(id);
        }

        return "redirect:/my-listings";
    }

    @PostMapping("/listing/{id}/remove")
    public String removeListing(@PathVariable int id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/signin";
        }

        if (listingService.isOwner(id, loggedInUser.getMemberID())) {
            listingService.deleteListing(id);
        }

        return "redirect:/my-listings";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q, Model model) {
        List<Listing> listings;
        if (q != null && !q.trim().isEmpty()) {
            listings = listingService.searchListings(q);
            model.addAttribute("searchTerm", q);
        } else {
            listings = listingService.getAllActiveListings();
        }

        model.addAttribute("listings", listings);
        return "search-results";
    }
}