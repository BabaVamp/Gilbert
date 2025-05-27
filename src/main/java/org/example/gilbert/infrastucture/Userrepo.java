package org.example.gilbert.infrastucture;


import org.example.gilbert.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Userrepo {


    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        String sql = "INSERT INTO User (Email, Password, FirstName, LastName) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());
        return user;
    }

    public User findByEmail(String email) {
        try {
            String sql = "SELECT * FROM User WHERE Email = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (Exception e) {
            return null;
        }
    }

    public User findById(int memberID) {
        try {
            String sql = "SELECT * FROM User WHERE MemberID = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), memberID);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE Email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public void updateUser(User user) {
        String sql = "UPDATE User SET Email = ?, FirstName = ?, LastName = ? WHERE MemberID = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getFirstName(), user.getLastName(), user.getMemberID());
    }

    public void deleteUser(int memberID) {
        String sql = "DELETE FROM User WHERE MemberID = ?";
        jdbcTemplate.update(sql, memberID);
    }
}


