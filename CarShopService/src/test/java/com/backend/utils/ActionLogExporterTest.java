package com.backend.utils;


import com.backend.model.ActionLog;
import com.backend.model.User;
import com.backend.util.ActionLogExporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ActionLogExporterTest  {

    private Path tempDir;
    private List<ActionLog> actionLogs;

    @BeforeEach
    public void setUp() throws IOException {
        // Создание временной директории
        tempDir = Files.createTempDirectory("testDir");

        // Инициализация тестовых данных
        actionLogs = new ArrayList<>();
        User testUser = new User(0, "testUser", "password", User.Role.CLIENT, "Test User", "test@example.com", "1234567890");

        actionLogs.add(new ActionLog(testUser, ActionLog.ActionType.CREATE, "Created a new entry"));
        actionLogs.add(new ActionLog(testUser, ActionLog.ActionType.UPDATE, "Updated an entry"));
        actionLogs.add(new ActionLog(testUser, ActionLog.ActionType.DELETE, "Deleted an entry"));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Удаление временной директории и файлов
        if (tempDir != null) {
            Files.walk(tempDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Test
    public void testExportToTextFile() {
        // Переопределение метода для использования временного файла
        String fileName = tempDir.resolve("testActionLog").toString();

        ActionLogExporter.exportToTextFile(actionLogs, fileName);

        File file = new File(fileName + ".txt");
        assertTrue(file.exists(), "File should be created");
        assertTrue(file.length() > 0, "File should not be empty");
    }

    @Test
    public void testCreateAndWriteFile() {
        String fileName = tempDir.resolve("testCreateAndWriteFile").toString();
        StringBuilder content = new StringBuilder("Test content");

        ActionLogExporter.createAndWriteFile(fileName, content);

        File file = new File(fileName + ".txt");
        assertTrue(file.exists(), "File should be created");
        assertTrue(file.length() > 0, "File should not be empty");

        try {
            String fileContent = Files.readString(file.toPath());
            assertEquals("Test content", fileContent.trim(), "File content should match");
        } catch (IOException e) {
            fail("Failed to read the file content: " + e.getMessage());
        }
    }
}