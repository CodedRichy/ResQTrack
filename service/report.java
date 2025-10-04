package service;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private int reportId;
    private int userId;
    private String description;
    private int casualties;
    private String damages;

    private static List<Report> reports = new ArrayList<>();

    public Report(int reportId, int userId, String description, int casualties, String damages) {
        this.reportId = reportId;
        this.userId = userId;
        this.description = description;
        this.casualties = casualties;
        this.damages = damages;
    }

    public void submitReport() {
        reports.add(this);
        System.out.println("Report submitted: " + description);
    }

    public static void viewReports() {
        System.out.println("\n===== REPORTS =====");
        if (reports.isEmpty()) {
            System.out.println("No reports available.");
        } else {
            for (Report r : reports) {
                System.out.println("Report ID: " + r.reportId);
                System.out.println("User ID: " + r.userId);
                System.out.println("Description: " + r.description);
                System.out.println("Casualties: " + r.casualties);
                System.out.println("Damages: " + r.damages);
                System.out.println("-------------------");
            }
        }
    }

    public static void main(String[] args) {
        Report r1 = new Report(1, 101, "Flood in city area", 5, "Houses damaged");
        r1.submitReport();

        Report r2 = new Report(2, 102, "Earthquake in town", 10, "Buildings collapsed");
        r2.submitReport();
        
        Report.viewReports();
    }
}