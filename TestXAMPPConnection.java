import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestXAMPPConnection {
    public static void main(String[] args) {
        System.out.println("Testing XAMPP MySQL Connection...");
        
        // XAMPP default MySQL configuration
        String url = "jdbc:mysql://localhost:3306/disaster_management_system";
        String username = "root";
        String password = "";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("✅ Database connection successful!");
            
            // Test if tables exist
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SHOW TABLES");
            
            System.out.println("\n📋 Available tables:");
            while (rs.next()) {
                System.out.println("  - " + rs.getString(1));
            }
            
            // Test user table
            rs = statement.executeQuery("SELECT COUNT(*) as user_count FROM users");
            if (rs.next()) {
                System.out.println("\n👥 Users in database: " + rs.getInt("user_count"));
            }
            
            // Test public info table
            rs = statement.executeQuery("SELECT COUNT(*) as info_count FROM public_info");
            if (rs.next()) {
                System.out.println("📢 Public info items: " + rs.getInt("info_count"));
            }
            
            System.out.println("\n🎉 XAMPP setup is working correctly!");
            System.out.println("You can now run your ResQTrack application.");
            
        } catch (Exception e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nTroubleshooting steps:");
            System.err.println("1. Make sure XAMPP MySQL is running");
            System.err.println("2. Check if database 'disaster_management_system' exists");
            System.err.println("3. Verify username 'admin' and password 'mifa@123'");
            System.err.println("4. Ensure MySQL Connector/J is in your classpath");
        }
    }
}
