package com.backend.view;

import com.backend.model.ActionLog;
import com.backend.util.SuccessResponses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class ActionLogExporter {

    public static void exportToTextFile(Map<UUID, ActionLog> actionLogs, String nameFile) {
        StringBuilder content = new StringBuilder();

        for (Map.Entry<UUID, ActionLog> entry : actionLogs.entrySet()) {
            ActionLog log = entry.getValue();
            content.append("ID: ").append(log.getId()).append("\n")
                    .append("User: ").append(log.getUser().getUserName()).append("\n")
                    .append("Action Type: ").append(log.getActionType()).append("\n")
                    .append("Action DateTime: ").append(log.getActionDateTime()).append("\n")
                    .append("Message: ").append(log.getMessage()).append("\n\n");
        }

        File file = new File(nameFile+".txt");
        try {
            if (file.createNewFile()) {
                SuccessResponses.printCustomMessage("File created: " + file.getName());
            } else {
                SuccessResponses.printCustomMessage("File already exists.");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write(content.toString());
                SuccessResponses.printCustomMessage("Data successfully exported to " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Failed to export data to file: " + e.getMessage());
        }
    }

}
