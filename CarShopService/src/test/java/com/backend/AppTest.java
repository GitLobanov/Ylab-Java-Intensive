package com.backend;


import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    public void testMain() throws Exception {
        UserRepository userRepository = new UserRepository();

        User user = userRepository.findById(1);
        System.out.println(user);

        userRepository.findAll().forEach(System.out::println);
    }
}