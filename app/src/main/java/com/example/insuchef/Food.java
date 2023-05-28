package com.example.insuchef;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import androidx.annotation.NonNull;

import java.util.ArrayList;


public class Food implements Parcelable {

    // Fields
    private String name;
    private double carbAmount;
    private double proteinAmount;
    private double fatAmount;
    private double calories;
    private boolean isSelected;

    private boolean isFavourite;
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

    protected Food(Parcel in) {
        name = in.readString();
        carbAmount = in.readDouble();
        proteinAmount = in.readDouble();
        fatAmount = in.readDouble();
        calories = in.readDouble();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    public boolean isFavourite() { return isFavourite; }

    // Food Methods

    public void setFavourite() {
        this.isFavourite = true;
    }

    public void removeFavourite() { this.isFavourite = false; }

    public void addToMeal(MealSelectionFragment mealFrag) {

        mealFrag.selectedMeal.add(this);
    }

    public void removeFromMeal(MealSelectionFragment mealFrag) {

        mealFrag.selectedMeal.remove(this);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Selection
    public boolean isSelected(){
        return this.isSelected;
    }

    public void toggleSelected(){
        this.isSelected = !this.isSelected;
    }

    public void removeSelected() { this.isSelected = false; }

    public static ArrayList<Food> setData(ArrayList<Food> meal){
        ArrayList<Food> foods = meal;
        /*String[] names = {"bread","apple","pear","carrot","strawberry","soup","milk"};
        int[] carbs = {30,40,20,10,25,35,45};
        for(int i=0;i<names.length;i++){
            Food food = new Food();
            food.setName(names[i]);
            food.setCarbAmount(carbs[i]);
            foods.add(food);
        }*/
        return foods;
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(carbAmount);
        parcel.writeDouble(proteinAmount);
        parcel.writeDouble(fatAmount);
        parcel.writeDouble(calories);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
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
