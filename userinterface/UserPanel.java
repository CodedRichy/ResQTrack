package userinterface;

import service.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private List<User> userDatabase;
    private JTabbedPane mainPane;
    private Map<String, JPanel> panels;

    public UserPanel(JTabbedPane mainPane, Map<String, JPanel> panels) {
        this.mainPane = mainPane;
        this.panels = panels;
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
            // Toggle admin mode for panels that support it
            boolean isAdmin = "Admin".equalsIgnoreCase(authenticatedUser.getRole());
            panels.values().forEach(p -> {
                if (p instanceof userinterface.AdminControllable) {
                    ((userinterface.AdminControllable) p).setAdminMode(isAdmin);
                }
            });

        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configureTabsForRole(String role) {
        String welcomePanelKey = "Shelters";

        switch (role) {
            case "Admin":
                enableTabs(new String[]{"Shelters", "Tasks", "SOS", "Reports", "Hazards", "PublicInfo"});
                welcomePanelKey = "Shelters";
                break;
            case "Responder":
                enableTabs(new String[]{"Tasks", "SOS", "Reports", "Hazards"});
                welcomePanelKey = "Tasks";
                break;
            case "Public":
                enableTabs(new String[]{"Shelters", "PublicInfo"});
                welcomePanelKey = "Shelters";
                break;
        }
        
        mainPane.setSelectedComponent(panels.get(welcomePanelKey));
        mainPane.setEnabledAt(0, false);
    }

    private void enableTabs(String[] panelKeys) {
        for (String key : panelKeys) {
            JPanel panelToEnable = panels.get(key);
            if (panelToEnable != null) {
                mainPane.setEnabledAt(mainPane.indexOfComponent(panelToEnable), true);
            }
        }
    }
}