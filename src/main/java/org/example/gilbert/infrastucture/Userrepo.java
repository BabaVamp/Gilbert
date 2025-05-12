package org.example.gilbert.infrastucture;


import org.example.gilbert.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Userrepo {


    private JdbcTemplate jdbcTemplate;

    public Userrepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User save(User user){
        String sql ="INSERT INTO  (USERNAME, PASSWORD, EMAIL) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql);
        return user;
    }

    public User findByEmail(String email){
        try{
            String sql ="SELECT * FROM USER WHERE EMAIL = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        }catch(Exception e){
            return null;
        }

    }


}


