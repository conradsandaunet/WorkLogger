package org.example;

import java.sql.Timestamp;
import java.util.UUID;

public class Session {

    private String id = UUID.randomUUID().toString();
    private String project;
    private Timestamp startTime;
    private Timestamp endTime;
    private String notes;

    public Session(String id, String project, Timestamp startTime, Timestamp endTime, String notes) {
        this.id = id;
        this.project = project;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
    }

    public String getId() {return id;}

    public String getProject() {return project;}

    public Timestamp getStartTime() {return startTime;}

    public Timestamp getEndTime() {return endTime;}

    public String getNotes() {return notes;}
}
