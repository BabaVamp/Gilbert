package org.example.gilbert.application;

import org.example.gilbert.domain.User;
import org.example.gilbert.infrastucture.Userrepo;
import org.springframework.stereotype.Service;


@Service
public class Userservice {

    private final Userrepo userrepo;

    public Userservice(Userrepo userrepo) {
        this.userrepo = userrepo;
    }


    public User SignIn(String Username, String Password) {
        String TestUser = "Test";
        String TestPassword = "123";

        System.out.println("login attempt" + TestUser);

        if (TestUser.equals(TestUser) && TestPassword.equals(Password)) {
            System.out.println("login successful");
            User user = new User();
            user.setUsername(TestUser);
            user.setPassword("[Password]");
            user.setFirstName("Test");
            user.setLastName("User");
            return user;

        }
        System.out.println("login failed");
        return null;
    }

    public User GetUserByUsername(String username) {
        String TestUser = "Test";
        return new User();
    }


    public User createUser(User user) {
        return userrepo.save(user);

    }

    public User findByEmail(String email) {
        return userrepo.findByEmail(email);
    }
}
