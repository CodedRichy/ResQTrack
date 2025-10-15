package main;

import userinterface.*;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Application Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(350, 400);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JButton userButton = new JButton("User Login");
            userButton.addActionListener(e -> new UserUI().setVisible(true));

            JButton shelterButton = new JButton("Manage Shelters");
            shelterButton.addActionListener(e -> new ShelterUI().setVisible(true));

            JButton taskButton = new JButton("Manage Tasks");
            taskButton.addActionListener(e -> new TaskUI().setVisible(true));

            JButton sosButton = new JButton("Manage SOS Alerts");
            sosButton.addActionListener(e -> new SosAlertUI().setVisible(true));
            
            JButton reportButton = new JButton("Manage Reports");
            reportButton.addActionListener(e -> new ReportUI().setVisible(true));

            panel.add(userButton);
            panel.add(shelterButton);
            panel.add(taskButton);
            panel.add(sosButton);
            panel.add(reportButton);

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}