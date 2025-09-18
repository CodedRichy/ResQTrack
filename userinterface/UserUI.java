package userinterface;

import service.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple Swing UI for user login.
 */
public class UserUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private List<User> userDatabase;

    public UserUI() {
        // For demonstration, we'll create a hardcoded list of users.
        // In a real application, this would come from a database.
        initializeUserDatabase();

        setTitle("User Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Use a more flexible layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Make button span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        add(loginButton, gbc);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    /**
     * Initializes a sample user database.
     */
    private void initializeUserDatabase() {
        userDatabase = new ArrayList<>();
        userDatabase.add(new User(1, "admin", "pass123", "Admin"));
        userDatabase.add(new User(2, "responder", "pass456", "Responder"));
        userDatabase.add(new User(3, "guest", "pass789", "Public"));
    }

    /**
     * Handles the login logic when the button is clicked.
     */
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User authenticatedUser = null;

        // Check credentials against the user database
        for (User user : userDatabase) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                authenticatedUser = user;
                break;
            }
        }

        if (authenticatedUser != null) {
            // Successful login
            JOptionPane.showMessageDialog(this,
                    "Welcome, " + authenticatedUser.getUsername() + "!\nYour role is: " + authenticatedUser.getRole(),
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            // Here you could open a new window based on the user's role
            // e.g., openAdminDashboard();
        } else {
            // Failed login
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
