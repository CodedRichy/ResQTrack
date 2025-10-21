package main;

import userinterface.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ResQTrack - Disaster Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            JTabbedPane tabbedPane = new JTabbedPane();

            JPanel shelterPanel = new ShelterPanel();
            JPanel taskPanel = new TaskPanel();
            JPanel sosAlertPanel = new SosAlertPanel();
            JPanel reportPanel = new ReportPanel();
            JPanel hazardPanel = new HazardPanel();
            JPanel publicInfoPanel = new PublicInfoPanel();

            Map<String, JPanel> panels = new HashMap<>();
            panels.put("Shelters", shelterPanel);
            panels.put("Tasks", taskPanel);
            panels.put("SOS", sosAlertPanel);
            panels.put("Reports", reportPanel);
            panels.put("Hazards", hazardPanel);
            panels.put("PublicInfo", publicInfoPanel);

            JPanel userPanel = new UserPanel(tabbedPane, panels);

            tabbedPane.addTab("User Login", userPanel);
            tabbedPane.addTab("Shelters", shelterPanel);
            tabbedPane.addTab("Tasks", taskPanel);
            tabbedPane.addTab("SOS Alerts", sosAlertPanel);
            tabbedPane.addTab("Reports", reportPanel);
            tabbedPane.addTab("Hazards", hazardPanel);
            tabbedPane.addTab("Public Info", publicInfoPanel);

            for (int i = 1; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setEnabledAt(i, false);
            }

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}