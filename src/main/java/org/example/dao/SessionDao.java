package org.example.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.Session;
import org.example.config.WorkloggerProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionDao {
    private MysqlDataSource sessionDS;

    public SessionDao() {
        sessionDS = new MysqlDataSource();
        sessionDS.setServerName(WorkloggerProperties.PROPS.getProperty("host"));
        sessionDS.setDatabaseName(WorkloggerProperties.PROPS.getProperty("db_name"));
        sessionDS.setPortNumber(Integer.parseInt(WorkloggerProperties.PROPS.getProperty("port")));
        sessionDS.setUser(WorkloggerProperties.PROPS.getProperty("uname"));
        sessionDS.setPassword(WorkloggerProperties.PROPS.getProperty("pwd"));
    }

    public Connection getConnection() throws SQLException {
        return sessionDS.getConnection();
    }

    public void startSession(String project) throws SQLException {
        String sql = "INSERT INTO sessions (id, project, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String id = java.util.UUID.randomUUID().toString();
            Timestamp startTime = new Timestamp(System.currentTimeMillis());

            stmt.setString(1, id);
            stmt.setString(2, project);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, null);

            stmt.executeUpdate();
            System.out.println("Session started for project: " + project + " (id=" + id + ")");
        }
    }

    public void stopSession() throws SQLException {
        String sql = "UPDATE sessions SET end_time = ? WHERE end_time IS NULL ORDER BY start_time DESC LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                System.out.println("Ongoing sessions stopped.");
            } else {
                System.out.println("No ongoing session to stop.");
            }
        }
    }

    public List<Session> getAllSessions() throws SQLException {

        List<Session> allSessions = new ArrayList<>();

        String sqlSessions = "SELECT * FROM sessions ORDER BY start_time DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlSessions);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String project = rs.getString("project");
                Timestamp start_time = rs.getTimestamp("start_time");
                Timestamp end_time = rs.getTimestamp("end_time");
                String notes = rs.getString("notes");

                Session session = new Session(id, project, start_time, end_time, notes);

                allSessions.add(session);

            }
        }
            return allSessions;
        }

    public long getTotalSeconds() throws SQLException {
        String sql = "SELECT SUM(TIMESTAMPDIFF(SECOND, start_time, end_time)) AS total_seconds FROM sessions";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getLong("total_seconds");
        }
        return 0;
    }
}

