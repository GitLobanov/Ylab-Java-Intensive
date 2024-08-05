package com.backend.service.user;

import com.backend.model.User;
import com.backend.repository.UserRepository;

/**
 * Service class for handling user login and registration.
 */
public class LoginRegisterService {

    /**
     * Registers a new user in the system.
     * Checks if a user with the same ID already exists in the repository.
     *
     * @param user the user to be registered
     * @return {@code true} if the user was successfully registered, {@code false} if a user with the same ID already exists
     */

    public boolean register(User user) {
        if (UserRepository.getInstance().findAll().containsKey(user.getId())) {
            return false;
        }
        UserRepository.getInstance().save(user);
        return true;
    }

    /**
     * Logs in a user by checking the provided username and password against the repository.
     *
     * @param userName the username of the user trying to log in
     * @param password the password of the user trying to log in
     * @return the {@code User} object if the login is successful, {@code null} if the username and password do not match
     */

    public User login(String userName, String password) {
        User user = UserRepository.getInstance().findByUserNameAndPassword(userName, password);
        if (user != null) {
            return user;
        }
        return null;
    }
}
