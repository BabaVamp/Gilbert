package org.example.gilbert.application;

import org.example.gilbert.domain.User;
import org.example.gilbert.infrastucture.Userrepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Userservice {

    private final Userrepo userrepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public Userservice(Userrepo userrepo) {
        this.userrepo = userrepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User signIn(String email, String password) {
        System.out.println("Login attempt for email: " + email);

        // Find user by email
        User user = userrepo.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Login successful for: " + email);
            // Don't return the password in the user object
            user.setPassword("[PROTECTED]");
            return user;
        }

        System.out.println("Login failed for: " + email);
        return null;
    }

    public User createUser(User user) throws Exception {
        // Check if user already exists
        if (userrepo.existsByEmail(user.getEmail())) {
            throw new Exception("User with this email already exists");
        }

        // Validate required fields
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new Exception("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new Exception("Password is required");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new Exception("First name is required");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new Exception("Last name is required");
        }

        // Validate email format (basic validation)
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new Exception("Invalid email format");
        }

        // Validate password length
        if (user.getPassword().length() < 6) {
            throw new Exception("Password must be at least 6 characters long");
        }

        // Set username if not provided
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            user.setUserName(user.getEmail().split("@")[0]); // Use email prefix as username
        }

        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        return userrepo.save(user);
    }

    public User findByEmail(String email) {
        return userrepo.findByEmail(email);
    }

    public User findById(int memberID) {
        return userrepo.findById(memberID);
    }

    public boolean isValidUser(String email, String password) {
        User user = userrepo.findByEmail(email);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    public void updateUser(User user) {
        userrepo.updateUser(user);
    }
}