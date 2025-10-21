import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestH2Connection {
    public static void main(String[] args) {
        System.out.println("Testing H2 Database Connection...");
        
        String url = "jdbc:h2:./resqtrack_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
        String username = "sa";
        String password = "";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("✅ H2 Database connection successful!");
            
            // Create a test table
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(50))");
            statement.execute("INSERT INTO test_table VALUES (1, 'Test Data')");
            
            // Test query
            ResultSet rs = statement.executeQuery("SELECT * FROM test_table");
            while (rs.next()) {
                System.out.println("Test data: " + rs.getInt("id") + " - " + rs.getString("name"));
            }
            
            System.out.println("🎉 H2 setup is working perfectly!");
            System.out.println("You can now run your ResQTrack application.");
            
        } catch (Exception e) {
            System.err.println("❌ H2 connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Make sure h2-2.2.224.jar is in your project folder.");
        }
    }
}

