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

    public void insertSession(Session session) throws SQLException {
        String sql = "INSERT INTO sessions "
                + "(id, project, start_time, end_time, notes) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, session.getId());
            stmt.setString(2, session.getProject());
            stmt.setTimestamp(3, session.getStartTime());
            stmt.setTimestamp(4, session.getEndTime());
            stmt.setString(5, session.getNotes());

            stmt.executeUpdate();
        }
    }

    public void getAllSessions() throws SQLException {

        List<Session> allSessions = new ArrayList<>();

        String sqlSessions = "SELECT * FROM sessions";

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
        }
    }

