package org.example.gilbert.domain;
import java.math.BigDecimal;

public class Listing {
    private int listingID;
    private int sellerID;
    private String title;
    private String description;
    private String brand;
    private String category;
    private String size;
    private String condition;
    private BigDecimal price;
    private String imageUrl;
    private String status; // ACTIVE, SOLD, REMOVED
    private String createdAt;
    private String updatedAt;

    public Listing() {
        this.status = "ACTIVE";
    }

    public Listing(int sellerID, String title, String description, String brand,
                   String category, String size, String condition, BigDecimal price, String imageUrl) {
        this.sellerID = sellerID;
        this.title = title;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.size = size;
        this.condition = condition;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public int getListingID() {
        return listingID;
    }

    public void setListingID(int listingID) {
        this.listingID = listingID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Listing{" +
                "listingID=" + listingID +
                ", title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
