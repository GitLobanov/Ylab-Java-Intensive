package com.backend.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.model.User;
import com.backend.repository.impl.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = UserRepository.getInstance();
        userRepository.findAll().clear(); // Ensure the repository is empty before each test
    }

    @Test
    void testSave() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("user1");
        user.setPassword("password1");

        boolean result = userRepository.save(user);
        assertTrue(result, "Save operation should return true");

        User fetchedUser = userRepository.findById(user.getId());
        assertNotNull(fetchedUser, "Saved user should be retrievable");
        assertEquals(user, fetchedUser, "Saved user should be equal to the fetched user");
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("user1");
        user.setPassword("password1");

        userRepository.save(user);

        user.setPassword("newpassword");
        boolean result = userRepository.update(user);
        assertTrue(result, "Update operation should return true");

        User fetchedUser = userRepository.findById(user.getId());
        assertNotNull(fetchedUser, "User should be retrievable");
        assertEquals("newpassword", fetchedUser.getPassword(), "Password should be updated");
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("user1");
        user.setPassword("password1");

        userRepository.save(user);

        boolean result = userRepository.delete(user);
        assertTrue(result, "Delete operation should return true");

        User fetchedUser = userRepository.findById(user.getId());
        assertNull(fetchedUser, "Deleted user should not be retrievable");
    }

    @Test
    void testFindByUserNameAndPassword() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("user1");
        user.setPassword("password1");

        userRepository.save(user);

        User fetchedUser = userRepository.findByUserNameAndPassword("user1", "password1");
        assertNotNull(fetchedUser, "User should be found by username and password");
        assertEquals(user, fetchedUser, "Fetched user should match the saved user");

        fetchedUser = userRepository.findByUserNameAndPassword("user1", "wrongpassword");
        assertNull(fetchedUser, "User with wrong password should not be found");
    }

    @Test
    void testFindByUserName() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUserName("user1");
        user.setPassword("password1");

        userRepository.save(user);

        User fetchedUser = userRepository.findByUserName("user1");
        assertNotNull(fetchedUser, "User should be found by username");
        assertEquals(user, fetchedUser, "Fetched user should match the saved user");

        fetchedUser = userRepository.findByUserName("nonexistentuser");
        assertNull(fetchedUser, "User with non-existent username should not be found");
    }

    @Test
    void testFindAll() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setUserName("user1");
        user1.setPassword("password1");

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setUserName("user2");
        user2.setPassword("password2");

        userRepository.save(user1);
        userRepository.save(user2);

        Map<UUID, User> allUsers = userRepository.findAll();
        assertEquals(2, allUsers.size(), "Should return all users in repository");
        assertTrue(allUsers.containsKey(user1.getId()), "All users should include user1");
        assertTrue(allUsers.containsKey(user2.getId()), "All users should include user2");
    }
}