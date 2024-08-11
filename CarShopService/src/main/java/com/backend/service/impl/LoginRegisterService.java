package com.backend.service.impl;

import com.backend.model.User;
import com.backend.repository.impl.UserRepository;


public class LoginRegisterService {

    UserRepository userRepository = new UserRepository();

    public boolean register(User user) {
        if (userRepository.findById(user.getId())!=null) {
            return false;
        }
        userRepository.save(user);
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
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if (user != null) {
            return user;
        }
        return null;
    }
}
