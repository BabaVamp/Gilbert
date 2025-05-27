
package org.example.gilbert.infrastucture;

import org.example.gilbert.domain.Listing;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListingRepo {

    private final JdbcTemplate jdbcTemplate;

    public ListingRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Listing save(Listing listing) {
        String sql = "INSERT INTO Listing (SellerID, Title, Description, Brand, Category, Size, Condition, Price, ImageUrl, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                listing.getSellerID(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getBrand(),
                listing.getCategory(),
                listing.getSize(),
                listing.getCondition(),
                listing.getPrice(),
                listing.getImageUrl(),
                listing.getStatus());
        return listing;
    }

    public List<Listing> findAllActive() {
        String sql = "SELECT * FROM Listing WHERE Status = 'ACTIVE' ORDER BY CreatedAt DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Listing.class));
    }

    public List<Listing> findBySellerID(int sellerID) {
        String sql = "SELECT * FROM Listing WHERE SellerID = ? ORDER BY CreatedAt DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Listing.class), sellerID);
    }

    public List<Listing> findByBrand(String brand) {
        String sql = "SELECT * FROM Listing WHERE Brand = ? AND Status = 'ACTIVE' ORDER BY CreatedAt DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Listing.class), brand);
    }

    public List<Listing> findByCategory(String category) {
        String sql = "SELECT * FROM Listing WHERE Category = ? AND Status = 'ACTIVE' ORDER BY CreatedAt DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Listing.class), category);
    }

    public Listing findById(int listingID) {
        try {
            String sql = "SELECT * FROM Listing WHERE ListingID = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Listing.class), listingID);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateStatus(int listingID, String status) {
        String sql = "UPDATE Listing SET Status = ?, UpdatedAt = CURRENT_TIMESTAMP WHERE ListingID = ?";
        jdbcTemplate.update(sql, status, listingID);
    }

    public void deleteListing(int listingID) {
        String sql = "DELETE FROM Listing WHERE ListingID = ?";
        jdbcTemplate.update(sql, listingID);
    }

    public List<Listing> searchListings(String searchTerm) {
        String sql = "SELECT * FROM Listing WHERE (Title LIKE ? OR Description LIKE ? OR Brand LIKE ?) AND Status = 'ACTIVE' ORDER BY CreatedAt DESC";
        String searchPattern = "%" + searchTerm + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Listing.class),
                searchPattern, searchPattern, searchPattern);
    }
}
