package service;

public class Report {

    private int reportId;
    private int userId;
    private String description;
    private int casualties;
    private String damages;

    public Report(int reportId, int userId, String description, int casualties, String damages) {
        this.reportId = reportId;
        this.userId = userId;
        this.description = description;
        this.casualties = casualties;
        this.damages = damages;
    }

    public int getReportId() {
        return reportId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public int getCasualties() {
        return casualties;
    }

    public String getDamages() {
        return damages;
    }
}