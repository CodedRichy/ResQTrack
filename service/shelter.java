package service;

import java.util.ArrayList;
import java.util.List;

public class Shelter {
    // Attributes
    private int shelterId;
    private String name;
    private int capacity;
    private int availableCapacity;
    private int food;
    private int water;
    private int medicine;

    // A static list to simulate a database for storing shelter objects
    private static List<Shelter> shelters = new ArrayList<>();

    /**
     * Constructor for Shelter class.
     * @param shelterId Unique identifier for the shelter.
     * @param name Name of the shelter.
     * @param capacity Total capacity of the shelter.
     * @param food Amount of food available (e.g., in units or rations).
     * @param water Amount of water available (e.g., in liters or bottles).
     * @param medicine Amount of medicine available (e.g., number of kits).
     */
    public Shelter(int shelterId, String name, int capacity, int food, int water, int medicine) {
        this.shelterId = shelterId;
        this.name = name;
        this.capacity = capacity;
        this.availableCapacity = capacity; // Initially, available capacity is the same as total capacity
        this.food = food;
        this.water = water;
        this.medicine = medicine;
    }

    // Methods

    /**
     * Adds the current shelter instance to the list of shelters.
     */
    public void addShelter() {
        shelters.add(this);
        System.out.println("Shelter added: " + this.name + " with ID: " + this.shelterId);
    }

    /**
     * Updates the details of the shelter, including its available capacity and resources.
     * @param newAvailableCapacity The new number of available spots in the shelter.
     * @param newFood The updated amount of food.
     * @param newWater The updated amount of water.
     * @param newMedicine The updated amount of medicine.
     */
    public void updateShelter(int newAvailableCapacity, int newFood, int newWater, int newMedicine) {
        if (newAvailableCapacity >= 0 && newAvailableCapacity <= this.capacity) {
            this.availableCapacity = newAvailableCapacity;
            this.food = newFood;
            this.water = newWater;
            this.medicine = newMedicine;
            System.out.println("Shelter " + this.name + " (ID: " + this.shelterId + ") updated successfully.");
        } else {
            System.out.println("Update failed for shelter " + this.name + ": Invalid available capacity.");
        }
    }

    /**
     * Prints the details of all shelters currently stored in the list.
     */
    public static void viewShelters() {
        System.out.println("\n===== ALL REGISTERED SHELTERS =====");
        if (shelters.isEmpty()) {
            System.out.println("No shelters are currently registered.");
        } else {
            for (Shelter s : shelters) {
                System.out.println("Shelter ID: " + s.shelterId);
                System.out.println("Name: " + s.name);
                System.out.println("Capacity: " + s.capacity);
                System.out.println("Available Capacity: " + s.availableCapacity);
                System.out.println("Food Supply: " + s.food);
                System.out.println("Water Supply: " + s.water);
                System.out.println("Medicine Supply: " + s.medicine);
                System.out.println("-----------------------------------");
            }
        }
        System.out.println("===================================\n");
    }

    public static void main(String[] args) {
        // Create and add new shelters
        Shelter s1 = new Shelter(1, "Community Center", 150, 500, 300, 100);
        s1.addShelter();

        Shelter s2 = new Shelter(2, "High School Gymnasium", 300, 800, 500, 150);
        s2.addShelter();

        // View all shelters to see initial state
        viewShelters();

        // Update a shelter after people have arrived and resources are used
        s1.updateShelter(120, 450, 280, 95);

        // View shelters again to see the updated information
        viewShelters();
    }
}