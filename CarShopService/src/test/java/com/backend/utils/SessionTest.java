package com.backend.utils;

import com.backend.model.User;
import com.backend.util.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        session = Session.getInstance();
        session.reset();
    }

    @Test
    public void testSingletonInstance() {
        Session instance1 = Session.getInstance();
        Session instance2 = Session.getInstance();
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    public void testActivateSession() {
        session.activate();
        assertTrue(session.isActive(), "Session should be active after activation");
    }

    @Test
    public void testDeactivateSession() {
        session.deactivate();
        assertFalse(session.isActive(), "Session should not be active after deactivation");
    }

    @Test
    public void testSetAndGetUser() {
        User user = new User("testUser", "password", User.Role.CLIENT, "Test User", "test@example.com", "1234567890");
        session.setUser(user);
        assertEquals(user, session.getUser(), "User should be set and retrieved correctly");
    }

    @Test
    public void testDefaultStage() {
        assertEquals(Session.Stage.HAVE_TO_LOGIN, session.getStage(), "Default stage should be HAVE_TO_LOGIN");
    }

    @Test
    public void testSetAndGetStage() {
        Session.getInstance().setStage(Session.Stage.ADMIN);
        assertEquals(Session.Stage.ADMIN, Session.getInstance().getStage(), "Stage should be set and retrieved correctly");
    }
}