package com.backend.service.user;

import com.backend.model.User;
import com.backend.repository.UserRepository;

public class LoginRegisterService {


    public boolean register(User user) {
        if (UserRepository.getInstance().findAll().containsKey(user.getId())) {
            return false; // Пользователь уже существует
        }
        UserRepository.getInstance().save(user);
        return true;
    }

    public User login(String userName, String password) {
        User user = UserRepository.getInstance().findByUserNameAndPassword(userName, password);
        if (user != null) {
            return user;
        }
        return null;
    }

}
