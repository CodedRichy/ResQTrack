package service;

public class Shelter {
    private int shelterId;
    private String name;
    private int capacity;
    private int availableCapacity;
    private int food;
    private int water;
    private int medicine;

    public Shelter(int shelterId, String name, int capacity, int food, int water, int medicine) {
        this.shelterId = shelterId;
        this.name = name;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.food = food;
        this.water = water;
        this.medicine = medicine;
    }

    public int getShelterId() { return shelterId; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public int getAvailableCapacity() { return availableCapacity; }
    public int getFood() { return food; }
    public int getWater() { return water; }
    public int getMedicine() { return medicine; }

    public boolean updateShelter(int newAvailableCapacity, int newFood, int newWater, int newMedicine) {
        if (newAvailableCapacity >= 0 && newAvailableCapacity <= this.capacity) {
            this.availableCapacity = newAvailableCapacity;
            this.food = newFood;
            this.water = newWater;
            this.medicine = newMedicine;
            return true;
        } else {
            return false;
        }
    }
}