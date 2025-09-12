import java.util.*;
package model;
public class PublicInfo {
    private List<String> alerts;
    private List<String> safetyGuidelines;
    private List<String> shelters;
    private List<String> emergencyContacts;
    
     public PublicInfo() {
        this.alerts = new ArrayList<>();
        this.safetyGuidelines = new ArrayList<>();
        this.shelters = new ArrayList<>();
        this.emergencyContacts = new ArrayList<>();
     }
        
         public void addAlert(String alert) {
        alerts.add(alert);
    }

    public void addSafetyGuideline(String guideline) {
        safetyGuidelines.add(guideline);
    }

    public void addShelterInfo(String shelter) {
        shelters.add(shelter);
    }

    public void addEmergencyContact(String contact) {
        emergencyContacts.add(contact);
    }

    public void displayPublicInfo() {
        System.out.println("PUBLIC INFORMATION");

        System.out.println("\nALERTS\n");
            for(String a : alerts){
                System.out.println("\n- " + a);
            }
              System.out.println("\nSAFETY GUIDELINES");
        for (String g : safetyGuidelines) {
            System.out.println("\n- " + g);
        }

        System.out.println("\nSHELTERS");
        for (String s : shelters) {
            System.out.println("\n- " + s);
        }

        System.out.println("\nEMERGENCY CONTACTS");
        for (String c : emergencyContacts) {
            System.out.println("\n- " + c);
        }
    }
    public static void main(String[] args) {
        PublicInfo info = new PublicInfo();

        // Adding sample data
        info.addAlert("Flood warning in coastal area - Red Alert");
        info.addSafetyGuideline("Do not step into floodwaters.");
        info.addSafetyGuideline("Keep emergency kit ready.");
        info.addShelterInfo("Shelter 1: Govt School, Capacity: 500");
        info.addEmergencyContact("Disaster Helpline: 108");
        info.addEmergencyContact("Police: 100");
        

        info.displayPublicInfo();
    }
}
