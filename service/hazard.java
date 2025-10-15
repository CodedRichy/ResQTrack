package service;

import java.util.ArrayList;
import java.util.List;

public class Hazard {
    private List<String> hazards;

    public Hazard() {
        this.hazards = new ArrayList<>();
    }

    public void addHazard(String hazard) {
        this.hazards.add(hazard);
        System.out.println("Hazard added: " + hazard);
    }

    public void viewHazard() {
        System.out.println("\n===== LIST OF HAZARDS =====");
        if (this.hazards.isEmpty()) {
            System.out.println("No hazards recorded.");
        } else {
            for (int i = 0; i < this.hazards.size(); i++) {
                System.out.println((i + 1) + ". " + this.hazards.get(i));
            }
        }
        System.out.println("===========================\n");
    }

    public static void main(String[] args) {
        Hazard hazardTracker = new Hazard();
        hazardTracker.addHazard("Fallen power line on Elm Street");
        hazardTracker.addHazard("Flooding near the river crossing");
        hazardTracker.viewHazard();
    }
}