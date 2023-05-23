package com.example.insuchef;

public class Food {

    // Fields
    private String name;
    private double carbAmount;
    private double proteinAmount;
    private double fatAmount;
    private double calories;
    private boolean isSelected;

    // Constructor
    Food(String name, double carb, double protein, double fat, double calories) {

        this.name = name;
        this.carbAmount = carb;
        this.proteinAmount = protein;
        this.fatAmount = fat;
        this.calories = calories;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getCarbAmount() {
        return carbAmount;
    }

    public double getProteinAmount() {
        return proteinAmount;
    }

    public double getFatAmount() {
        return fatAmount;
    }

    public double getEnergyAmount() {
        return calories;
    }

    public String toString() {
        return this.name;
    }

    // Food methods
    public boolean addToMeal() {
        return true;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void toggleSelected(){
        this.isSelected = !this.isSelected;
    }




}
