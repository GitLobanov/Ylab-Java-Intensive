package com.backend.service;

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


    public User login(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if (user != null) {
            return user;
        }
        return null;
    }
}
