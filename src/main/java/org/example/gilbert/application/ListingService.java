package org.example.gilbert.application;

import org.example.gilbert.domain.Listing;
import org.example.gilbert.infrastucture.ListingRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ListingService {

    private final ListingRepo listingRepo;

    public ListingService(ListingRepo listingRepo) {
        this.listingRepo = listingRepo;
    }

    public Listing createListing(Listing listing) throws Exception {
        // Validate required fields
        if (listing.getTitle() == null || listing.getTitle().trim().isEmpty()) {
            throw new Exception("Title is required");
        }
        if (listing.getDescription() == null || listing.getDescription().trim().isEmpty()) {
            throw new Exception("Description is required");
        }
        if (listing.getBrand() == null || listing.getBrand().trim().isEmpty()) {
            throw new Exception("Brand is required");
        }
        if (listing.getCategory() == null || listing.getCategory().trim().isEmpty()) {
            throw new Exception("Category is required");
        }
        if (listing.getCondition() == null || listing.getCondition().trim().isEmpty()) {
            throw new Exception("Condition is required");
        }
        if (listing.getPrice() == null || listing.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Price must be greater than 0");
        }
        if (listing.getSellerID() <= 0) {
            throw new Exception("Valid seller ID is required");
        }

        // Set default values
        if (listing.getStatus() == null) {
            listing.setStatus("ACTIVE");
        }

        return listingRepo.save(listing);
    }

    public List<Listing> getAllActiveListings() {
        return listingRepo.findAllActive();
    }

    public List<Listing> getListingsBySellerID(int sellerID) {
        return listingRepo.findBySellerID(sellerID);
    }

    public List<Listing> getListingsByBrand(String brand) {
        return listingRepo.findByBrand(brand);
    }

    public List<Listing> getListingsByCategory(String category) {
        return listingRepo.findByCategory(category);
    }

    public Listing getListingById(int listingID) {
        return listingRepo.findById(listingID);
    }

    public void markAsSold(int listingID) {
        listingRepo.updateStatus(listingID, "SOLD");
    }

    public void markAsRemoved(int listingID) {
        listingRepo.updateStatus(listingID, "REMOVED");
    }

    public void deleteListing(int listingID) {
        listingRepo.deleteListing(listingID);
    }

    public List<Listing> searchListings(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllActiveListings();
        }
        return listingRepo.searchListings(searchTerm.trim());
    }

    public boolean isOwner(int listingID, int userID) {
        Listing listing = listingRepo.findById(listingID);
        return listing != null && listing.getSellerID() == userID;
    }
}