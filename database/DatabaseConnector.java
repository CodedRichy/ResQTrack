package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnector {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/disaster_management_system";
    private static final String USER = "admin";
    private static final String PASS = "mifa@123";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Database Connection Error: Failed to connect to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Database connection successful!");
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the connection.");
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to establish a database connection. Please check your configuration.");
        }
    }
}
