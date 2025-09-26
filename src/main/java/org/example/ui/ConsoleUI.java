package org.example.ui;

import org.example.dao.SessionDao;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;
    private final SessionDao sessionDao;
    private boolean running;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.sessionDao = new SessionDao();
    }

    public void start() throws SQLException {
        running = true;

        while (running) {
            System.out.println("\n=== WorkLogger Menu ===");
            System.out.println("1. Start session.");
            System.out.println("2. Stop session.");
            System.out.println("3. Get all sessions.");
            System.out.println("4. Total time worked.");
            System.out.println("5. Total time worked on a project.");
            System.out.println("0. Exit.");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Project: ");
                    String project = scanner.nextLine();
                    System.out.println("Notes (optional): ");
                    String notes = scanner.nextLine();
                    sessionDao.startSession(project, notes);
                    break;
                case 2:
                    sessionDao.stopSession();
                    break;
                case 3:
                    if (sessionDao.getAllSessions().isEmpty()) {
                        System.out.println("No sessions logged.");
                    }
                    sessionDao.getAllSessions().forEach(System.out::println);
                    break;
                case 4:
                    long totalSeconds = sessionDao.getTotalSeconds();
                    long hours = totalSeconds / 3600;
                    long minutes = (totalSeconds % 3600) / 60;
                    System.out.println("Total time: " + hours + "h " + minutes + "m");
                    break;
                case 5:
                    System.out.println("Project: ");
                    String projectName = scanner.nextLine();
                    long projectSeconds = sessionDao.getTimeWorkedOnProject(projectName);
                    long projectHours = projectSeconds / 3600;
                    long projectMinutes = (projectSeconds % 3600) / 60;
                    System.out.println("Time worked on project '" + projectName + "': " + projectHours + "h " + projectMinutes + "m");
                    break;
                case 0:
                    System.out.println("Quitting...");
                    running = false;
                    break;
                default:
                    System.out.println("Not a valid option.");
            }
        }
    }
}
