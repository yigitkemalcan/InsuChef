package com.example.insuchef;

import java.util.ArrayList;

public class Food {

    // Fields
    private String name;
    private double carbAmount;
    private double proteinAmount;
    private double fatAmount;
    private double calories;
    private boolean isSelected;
    private boolean isLocked = false;
    private int gram=-1;

    // Constructor
    Food(String name, double carb, double protein, double fat, double calories) {

        this.name = name;
        this.carbAmount = carb;
        this.proteinAmount = protein;
        this.fatAmount = fat;
        this.calories = calories;
    }
    Food(){}

    // Getters
    public String getName() {
        return name;
    }

    public double getCarbAmount() {
        return carbAmount;
    }
    public double getCarbAmountRespectToGram(){
        return carbAmount*gram/100;
    }
    public double getCarbAmountRespectToGram(double gram){
        return carbAmount*gram/100;
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
    public static ArrayList<Food> setData(){
        ArrayList<Food> foods = new ArrayList<>();
        String[] names = {"bread","apple","pear","carrot","strawberry","soup","milk"};
        int[] carbs = {30,40,20,10,25,35,45};
        for(int i=0;i<names.length;i++){
            Food food = new Food();
            food.setName(names[i]);
            food.setCarbAmount(carbs[i]);
            foods.add(food);
        }
        return foods;
    }

    public void setCarbAmount(double carbAmount) {
        this.carbAmount = carbAmount;
    }

    public void setProteinAmount(double proteinAmount) {
        this.proteinAmount = proteinAmount;
    }

    public void setFatAmount(double fatAmount) {
        this.fatAmount = fatAmount;
    }

    private void setCaloryAmount(int calory) {
        this.calories = calory;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getGram() {
        return gram;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }
}
