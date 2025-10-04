package service;

import java.util.ArrayList;
import java.util.List;

public class Hazard {
    private List<String> hazards;

    public Hazard() {
        hazards = new ArrayList<>();
    }

    public void addHazard(String hazard) {
        hazards.add(hazard);
        System.out.println("Hazard added: " + hazard);
    }

    public void viewHazard() {
        System.out.println("\n===== LIST OF HAZARDS =====");
        if (hazards.isEmpty()) {
            System.out.println("No hazards recorded.");
        } else {
            for (int i = 0; i < hazards.size(); i++) {
                System.out.println((i + 1) + ". " + hazards.get(i));
            }
        }
        System.out.println("===========================\n");
    }
}