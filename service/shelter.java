package service;

import java.util.ArrayList;
import java.util.List;

public class Shelter {
    private int shelterId;
    private String name;
    private int capacity;
    private int availableCapacity;
    private int food;
    private int water;
    private int medicine;

    private static List<Shelter> shelters = new ArrayList<>();

    public Shelter(int shelterId, String name, int capacity, int food, int water, int medicine) {
        this.shelterId = shelterId;
        this.name = name;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.food = food;
        this.water = water;
        this.medicine = medicine;
    }

    public void addShelter() {
        shelters.add(this);
        System.out.println("Shelter added: " + this.name + " with ID: " + this.shelterId);
    }

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
        Shelter s1 = new Shelter(1, "Community Center", 150, 500, 300, 100);
        s1.addShelter();

        Shelter s2 = new Shelter(2, "High School Gymnasium", 300, 800, 500, 150);
        s2.addShelter();

        viewShelters();

        s1.updateShelter(120, 450, 280, 95);

        viewShelters();
    }
}