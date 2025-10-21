package database;

import service.*;
import java.util.List;

public class DatabaseService {
    private static DatabaseService instance;
    private UserDAO userDAO;
    private ShelterDAO shelterDAO;
    private TaskDAO taskDAO;
    private SosAlertDAO sosAlertDAO;
    private ReportDAO reportDAO;
    private HazardDAO hazardDAO;
    private PublicInfoDAO publicInfoDAO;
    
    private DatabaseService() {
        this.userDAO = new UserDAO();
        this.shelterDAO = new ShelterDAO();
        this.taskDAO = new TaskDAO();
        this.sosAlertDAO = new SosAlertDAO();
        this.reportDAO = new ReportDAO();
        this.hazardDAO = new HazardDAO();
        this.publicInfoDAO = new PublicInfoDAO();
    }
    
    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }
    
    // User operations
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
    
    public User getUserById(int userId) {
        return userDAO.findById(userId);
    }
    
    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
    
    public User authenticateUser(String username, String password) {
        return userDAO.authenticateUser(username, password);
    }
    
    public List<User> getUsersByRole(String role) {
        return userDAO.findByRole(role);
    }
    
    public boolean createUser(User user) {
        return userDAO.insert(user);
    }
    
    public boolean updateUser(User user) {
        return userDAO.update(user);
    }
    
    public boolean deleteUser(int userId) {
        return userDAO.delete(userId);
    }
    
    // Shelter operations
    public List<Shelter> getAllShelters() {
        return shelterDAO.findAll();
    }
    
    public Shelter getShelterById(int shelterId) {
        return shelterDAO.findById(shelterId);
    }
    
    public List<Shelter> getAvailableShelters() {
        return shelterDAO.findAvailableShelters();
    }
    
    public List<Shelter> getSheltersByLocation(String location) {
        return shelterDAO.findSheltersByLocation(location);
    }
    
    public boolean createShelter(Shelter shelter) {
        return shelterDAO.insert(shelter);
    }
    
    public boolean updateShelter(Shelter shelter) {
        return shelterDAO.update(shelter);
    }
    
    public boolean updateShelterResources(int shelterId, int newAvailableCapacity, int newFood, int newWater, int newMedicine) {
        return shelterDAO.updateShelterResources(shelterId, newAvailableCapacity, newFood, newWater, newMedicine);
    }
    
    public boolean deleteShelter(int shelterId) {
        return shelterDAO.delete(shelterId);
    }
    
    // Task operations
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }
    
    public Task getTaskById(int taskId) {
        return taskDAO.findById(taskId);
    }
    
    public List<Task> getTasksByUser(int userId) {
        return taskDAO.findByAssignedUser(userId);
    }
    
    public List<Task> getTasksByStatus(String status) {
        return taskDAO.findByStatus(status);
    }
    
    public List<Task> getPendingTasks() {
        return taskDAO.findPendingTasks();
    }
    
    public List<Task> getInProgressTasks() {
        return taskDAO.findInProgressTasks();
    }
    
    public List<Task> getCompletedTasks() {
        return taskDAO.findCompletedTasks();
    }
    
    public List<Task> getTasksByLocation(String location) {
        return taskDAO.findByLocation(location);
    }
    
    public boolean createTask(Task task) {
        return taskDAO.insert(task);
    }
    
    public boolean updateTask(Task task) {
        return taskDAO.update(task);
    }
    
    public boolean updateTaskStatus(int taskId, String status) {
        return taskDAO.updateTaskStatus(taskId, status);
    }
    
    public boolean deleteTask(int taskId) {
        return taskDAO.delete(taskId);
    }
    
    // SOS Alert operations
    public List<SosAlert> getAllSosAlerts() {
        return sosAlertDAO.findAll();
    }
    
    public SosAlert getSosAlertById(int alertId) {
        return sosAlertDAO.findById(alertId);
    }
    
    public List<SosAlert> getSosAlertsByUser(int userId) {
        return sosAlertDAO.findByUser(userId);
    }
    
    public List<SosAlert> getActiveSosAlerts() {
        return sosAlertDAO.findActiveAlerts();
    }
    
    public List<SosAlert> getSosAlertsByStatus(String status) {
        return sosAlertDAO.findByStatus(status);
    }
    
    public List<SosAlert> getSosAlertsByLocation(String location) {
        return sosAlertDAO.findByLocation(location);
    }
    
    public List<SosAlert> getRecentSosAlerts(int hours) {
        return sosAlertDAO.findRecentAlerts(hours);
    }
    
    public boolean createSosAlert(SosAlert alert) {
        return sosAlertDAO.insert(alert);
    }
    
    public boolean updateSosAlert(SosAlert alert) {
        return sosAlertDAO.update(alert);
    }
    
    public boolean updateSosAlertStatus(int alertId, String status) {
        return sosAlertDAO.updateAlertStatus(alertId, status);
    }
    
    public boolean deleteSosAlert(int alertId) {
        return sosAlertDAO.delete(alertId);
    }
    
    // Report operations
    public List<Report> getAllReports() {
        return reportDAO.findAll();
    }
    
    public Report getReportById(int reportId) {
        return reportDAO.findById(reportId);
    }
    
    public List<Report> getReportsByUser(int userId) {
        return reportDAO.findByUser(userId);
    }
    
    public List<Report> getReportsByStatus(String status) {
        return reportDAO.findByStatus(status);
    }
    
    public List<Report> getPendingReports() {
        return reportDAO.findPendingReports();
    }
    
    public List<Report> getUnderReviewReports() {
        return reportDAO.findUnderReviewReports();
    }
    
    public List<Report> getResolvedReports() {
        return reportDAO.findResolvedReports();
    }
    
    public List<Report> getReportsWithCasualties() {
        return reportDAO.findReportsWithCasualties();
    }
    
    public List<Report> getRecentReports(int days) {
        return reportDAO.findRecentReports(days);
    }
    
    public boolean createReport(Report report) {
        return reportDAO.insert(report);
    }
    
    public boolean updateReport(Report report) {
        return reportDAO.update(report);
    }
    
    public boolean updateReportStatus(int reportId, String status) {
        return reportDAO.updateReportStatus(reportId, status);
    }
    
    public boolean deleteReport(int reportId) {
        return reportDAO.delete(reportId);
    }
    
    // Hazard operations
    public List<Hazard> getAllHazards() {
        return hazardDAO.findAll();
    }
    
    public Hazard getHazardById(int hazardId) {
        return hazardDAO.findById(hazardId);
    }
    
    public List<Hazard> getActiveHazards() {
        return hazardDAO.findActiveHazards();
    }
    
    public List<Hazard> getHazardsByStatus(String status) {
        return hazardDAO.findByStatus(status);
    }
    
    public List<Hazard> getHazardsByLocation(String location) {
        return hazardDAO.findByLocation(location);
    }
    
    public List<Hazard> getHazardsBySeverity(String severity) {
        return hazardDAO.findBySeverity(severity);
    }
    
    public List<Hazard> getCriticalHazards() {
        return hazardDAO.findCriticalHazards();
    }
    
    public List<Hazard> getHighSeverityHazards() {
        return hazardDAO.findHighSeverityHazards();
    }
    
    public List<Hazard> getRecentHazards(int hours) {
        return hazardDAO.findRecentHazards(hours);
    }
    
    public boolean createHazard(Hazard hazard) {
        return hazardDAO.insert(hazard);
    }
    
    public boolean updateHazard(Hazard hazard) {
        return hazardDAO.update(hazard);
    }
    
    public boolean updateHazardStatus(int hazardId, String status) {
        return hazardDAO.updateHazardStatus(hazardId, status);
    }
    
    public boolean deleteHazard(int hazardId) {
        return hazardDAO.delete(hazardId);
    }
    
    // Public Info operations
    public List<PublicInfo> getAllPublicInfo() {
        return publicInfoDAO.findAll();
    }
    
    public PublicInfo getPublicInfoById(int infoId) {
        return publicInfoDAO.findById(infoId);
    }
    
    public List<PublicInfo> getActivePublicInfo() {
        return publicInfoDAO.findActiveInfo();
    }
    
    public List<PublicInfo> getPublicInfoByType(String type) {
        return publicInfoDAO.findByType(type);
    }
    
    public List<PublicInfo> getPublicInfoByStatus(String status) {
        return publicInfoDAO.findByStatus(status);
    }
    
    public List<PublicInfo> getAlerts() {
        return publicInfoDAO.findAlerts();
    }
    
    public List<PublicInfo> getGuidelines() {
        return publicInfoDAO.findGuidelines();
    }
    
    public List<PublicInfo> getShelterInfo() {
        return publicInfoDAO.findShelterInfo();
    }
    
    public List<PublicInfo> getContactInfo() {
        return publicInfoDAO.findContactInfo();
    }
    
    public List<PublicInfo> getRecentPublicInfo(int days) {
        return publicInfoDAO.findRecentInfo(days);
    }
    
    public boolean createPublicInfo(PublicInfo info) {
        return publicInfoDAO.insert(info);
    }
    
    public boolean updatePublicInfo(PublicInfo info) {
        return publicInfoDAO.update(info);
    }
    
    public boolean updatePublicInfoStatus(int infoId, String status) {
        return publicInfoDAO.updateInfoStatus(infoId, status);
    }
    
    public boolean deletePublicInfo(int infoId) {
        return publicInfoDAO.delete(infoId);
    }
    
    // Utility methods
    public void closeAllConnections() {
        userDAO.closeConnection();
        shelterDAO.closeConnection();
        taskDAO.closeConnection();
        sosAlertDAO.closeConnection();
        reportDAO.closeConnection();
        hazardDAO.closeConnection();
        publicInfoDAO.closeConnection();
    }
}

