package userinterface;

import service.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private List<User> userDatabase;
    private JTabbedPane mainPane;

    public UserPanel(JTabbedPane mainPane) {
        this.mainPane = mainPane;
        initializeUserDatabase();

        this.setLayout(new GridBagLayout());
        
        JPanel loginFormPanel = new JPanel(new GridBagLayout());
        loginFormPanel.setBorder(BorderFactory.createTitledBorder("Login Required"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFormPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(15);
        loginFormPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginFormPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        loginFormPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("Login");
        loginFormPanel.add(loginButton, gbc);

        this.add(loginFormPanel, new GridBagConstraints());

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void initializeUserDatabase() {
        userDatabase = new ArrayList<>();
        userDatabase.add(new User(1, "admin", "pass123", "Admin"));
        userDatabase.add(new User(2, "responder", "pass456", "Responder"));
        userDatabase.add(new User(3, "guest", "pass789", "Public"));
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        User authenticatedUser = null;

        for (User user : userDatabase) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                authenticatedUser = user;
                break;
            }
        }

        if (authenticatedUser != null) {
            JOptionPane.showMessageDialog(this,
                    "Welcome, " + authenticatedUser.getUsername() + "!\nYour role is: " + authenticatedUser.getRole(),
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            
            configureTabsForRole(authenticatedUser.getRole());

        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configureTabsForRole(String role) {
        int welcomeTab = 1;

        switch (role) {
            case "Admin":
                enableTabs(new int[]{1, 2, 3, 4, 5, 6});
                welcomeTab = 1;
                break;
            case "Responder":
                enableTabs(new int[]{2, 3, 4, 5});
                welcomeTab = 2;
                break;
            case "Public":
                enableTabs(new int[]{1, 6});
                welcomeTab = 1;
                break;
        }
        
        mainPane.setSelectedIndex(welcomeTab);
        mainPane.setEnabledAt(0, false);
    }

    private void enableTabs(int[] tabIndices) {
        for (int index : tabIndices) {
            if (index < mainPane.getTabCount()) {
                mainPane.setEnabledAt(index, true);
            }
        }
    }
}