package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DatabaseInitializer {
    
    public static void initializeDatabase() {
        Connection connection = null;
        Statement statement = null;
        
        try {
            // Get connection to MySQL server (without specifying database)
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/", 
                "admin", 
                "mifa@123"
            );
            
            statement = connection.createStatement();
            
            // Read and execute the schema file
            String schemaPath = "database/DatabaseSchema.sql";
            String schema = readFile(schemaPath);
            
            // Split the schema into individual statements
            String[] statements = schema.split(";");
            
            for (String sql : statements) {
                sql = sql.trim();
                if (!sql.isEmpty() && !sql.startsWith("--")) {
                    try {
                        statement.execute(sql);
                        System.out.println("Executed: " + sql.substring(0, Math.min(sql.length(), 50)) + "...");
                    } catch (SQLException e) {
                        // Ignore errors for statements that might already exist
                        if (!e.getMessage().contains("already exists")) {
                            System.err.println("Error executing: " + sql);
                            System.err.println("Error: " + e.getMessage());
                        }
                    }
                }
            }
            
            System.out.println("Database initialization completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading schema file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("Initializing ResQTrack Database...");
        initializeDatabase();
    }
}
