import database.*;
import service.*;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("Testing ResQTrack Database Connection...");
        
        try {
            // Test database connection
            DatabaseService dbService = DatabaseService.getInstance();
            
            // Test user operations
            System.out.println("\n=== Testing User Operations ===");
            var users = dbService.getAllUsers();
            System.out.println("Found " + users.size() + " users in database");
            
            // Test authentication
            User admin = dbService.authenticateUser("admin", "admin123");
            if (admin != null) {
                System.out.println("Admin authentication successful: " + admin.getUsername() + " (" + admin.getRole() + ")");
            } else {
                System.out.println("Admin authentication failed");
            }
            
            // Test public info operations
            System.out.println("\n=== Testing Public Info Operations ===");
            var publicInfo = dbService.getAllPublicInfo();
            System.out.println("Found " + publicInfo.size() + " public info items");
            
            // Test shelter operations
            System.out.println("\n=== Testing Shelter Operations ===");
            var shelters = dbService.getAllShelters();
            System.out.println("Found " + shelters.size() + " shelters");
            
            // Test task operations
            System.out.println("\n=== Testing Task Operations ===");
            var tasks = dbService.getAllTasks();
            System.out.println("Found " + tasks.size() + " tasks");
            
            // Test SOS alert operations
            System.out.println("\n=== Testing SOS Alert Operations ===");
            var sosAlerts = dbService.getAllSosAlerts();
            System.out.println("Found " + sosAlerts.size() + " SOS alerts");
            
            // Test report operations
            System.out.println("\n=== Testing Report Operations ===");
            var reports = dbService.getAllReports();
            System.out.println("Found " + reports.size() + " reports");
            
            // Test hazard operations
            System.out.println("\n=== Testing Hazard Operations ===");
            var hazards = dbService.getAllHazards();
            System.out.println("Found " + hazards.size() + " hazards");
            
            System.out.println("\n=== Database Test Completed Successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Database test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

