package com.backend.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.repository.ActionLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

class ActionLogRepositoryTest {

    private ActionLogRepository actionLogRepository;

    @BeforeEach
    void setUp() {
        actionLogRepository = ActionLogRepository.getInstance();
        actionLogRepository.findAll().clear(); // Ensure the repository is empty before each test
    }

    @Test
    void testSave() {
        User user = new User();
        user.setId(UUID.randomUUID());

        ActionLog actionLog = new ActionLog();
        actionLog.setId(UUID.randomUUID());
        actionLog.setUser(user);
        actionLog.setActionType(ActionLog.ActionType.CREATE);
        actionLog.setActionDateTime(LocalDateTime.now());
        actionLog.setMessage("Test action");

        boolean result = actionLogRepository.save(actionLog);
        assertTrue(result, "Save operation should return true");

        ActionLog fetchedLog = actionLogRepository.findById(actionLog.getId());
        assertNotNull(fetchedLog, "Saved action log should be retrievable");
        assertEquals(actionLog, fetchedLog, "Saved action log should be equal to the fetched log");
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setId(UUID.randomUUID());

        ActionLog actionLog = new ActionLog();
        actionLog.setId(UUID.randomUUID());
        actionLog.setUser(user);
        actionLog.setActionType(ActionLog.ActionType.CREATE);
        actionLog.setActionDateTime(LocalDateTime.now());
        actionLog.setMessage("Test action");

        actionLogRepository.save(actionLog);

        actionLog.setMessage("Updated description");
        boolean result = actionLogRepository.update(actionLog);
        assertTrue(result, "Update operation should return true");

        ActionLog fetchedLog = actionLogRepository.findById(actionLog.getId());
        assertNotNull(fetchedLog, "Action log should be retrievable");
        assertEquals("Updated description", fetchedLog.getMessage(), "Description should be updated");
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setId(UUID.randomUUID());

        ActionLog actionLog = new ActionLog();
        actionLog.setId(UUID.randomUUID());
        actionLog.setUser(user);
        actionLog.setActionType(ActionLog.ActionType.CREATE);
        actionLog.setActionDateTime(LocalDateTime.now());
        actionLog.setMessage("Test action");

        actionLogRepository.save(actionLog);

        boolean result = actionLogRepository.delete(actionLog);
        assertTrue(result, "Delete operation should return true");

        ActionLog fetchedLog = actionLogRepository.findById(actionLog.getId());
        assertNull(fetchedLog, "Deleted action log should not be retrievable");
    }

    @Test
    void testFindByUser() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());

        User user2 = new User();
        user2.setId(UUID.randomUUID());

        ActionLog log1 = new ActionLog();
        log1.setId(UUID.randomUUID());
        log1.setUser(user1);
        log1.setActionType(ActionLog.ActionType.CREATE);
        log1.setActionDateTime(LocalDateTime.now());
        log1.setMessage("User1 action");

        ActionLog log2 = new ActionLog();
        log2.setId(UUID.randomUUID());
        log2.setUser(user2);
        log2.setActionType(ActionLog.ActionType.DELETE);
        log2.setActionDateTime(LocalDateTime.now());
        log2.setMessage("User2 action");

        actionLogRepository.save(log1);
        actionLogRepository.save(log2);

        Map<UUID, ActionLog> logsForUser1 = actionLogRepository.findByUser(user1);
        assertEquals(1, logsForUser1.size(), "Should return only logs for user1");
        assertTrue(logsForUser1.containsKey(log1.getId()), "Logs should include log1");

        Map<UUID, ActionLog> logsForUser2 = actionLogRepository.findByUser(user2);
        assertEquals(1, logsForUser2.size(), "Should return only logs for user2");
        assertTrue(logsForUser2.containsKey(log2.getId()), "Logs should include log2");
    }
}