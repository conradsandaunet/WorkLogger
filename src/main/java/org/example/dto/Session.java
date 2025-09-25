package org.example.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class Session {

    private String id = UUID.randomUUID().toString();
    private final String project;
    private final Timestamp startTime;
    private final Timestamp endTime;
    private final String notes;

    public Session(String id, String project, Timestamp startTime, Timestamp endTime, String notes) {
        this.id = id;
        this.project = project;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
    }

    @Override
    public String toString() {
        String startStr = startTime != null ? startTime.toString() : "N/A";
        String endStr = endTime != null ? endTime.toString() : "Ongoing";
        long durationMinutes = 0;
        if (startTime != null && endTime != null) {
            durationMinutes = (endTime.getTime() - startTime.getTime()) / 60000;
        }
        return String.format("ID: %s | Project: %s | Start: %s | End: %s | Duration: %d min | Notes: %s",
                id, project, startStr, endStr, durationMinutes, notes != null ? notes : "");
    }
}
