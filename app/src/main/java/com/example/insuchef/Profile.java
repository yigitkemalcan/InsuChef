package com.example.insuchef;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// this class holds peoples information in different person objects
// this class can be used when creating the profile page
public class Profile {
    private String name;
    private double weight;
    private int breakfastRestriction;
    private int lunchRestriction;
    private int dinnerRestriction;
    private int targetBloodSugar;
    private int diabetesAge; // how many years does the patient deal with diabetes
    private Calc calc;
    private ArrayList<String> favourites;
    private int insulinSensivity;
    private int instantBloodSugar;
    private int carbInsulinRatio;

    private ArrayList<Food> addedFoods;

    // Constructor
    public Profile(){ }



    // getters
    public String getName(){
        return this.name;
    }

    /*public int getAge(){
        return this.age;
    }*/

    public double getWeight(){
        return this.weight;
    }

    public int getTargetBloodSugar(){
        return this.targetBloodSugar;
    }

    public int getDiabetesAge(){
        return this.diabetesAge;
    }

    public Calc getCalc(){
        return this.calc;
    }

    public int getBreakfastRestriction() {
        return breakfastRestriction;
    }

    public int getLunchRestriction() {
        return lunchRestriction;
    }

    public int getDinnerRestriction() {
        return dinnerRestriction;
    }
  
    public int getInsulinSensivity(){
        return this.insulinSensivity;
    }

    public ArrayList<String> getFavourites() { return favourites; }

    //setters

    public void setName(String name){
        this.name = name;
    }

   /* public void setAge(int age){
        this.age = age;
    }*/

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setTargetBloodSugar(int targetbloodsugar){
        this.targetBloodSugar = targetbloodsugar;
    }

    public void setDiabetesAge(int diabetesage){
        this.diabetesAge = diabetesage;
    }

    public void setCalc(double weight, int targetBloodSugar, int numberOfMeals){
        this.calc = new Calc(weight, targetBloodSugar, numberOfMeals);
    }

    public void setCalc(double weight, int targetBloodSugar, int numberOfMeals, int diabetesage){
        this.calc = new Calc(weight, targetBloodSugar, numberOfMeals, diabetesage);
    }

    public void setBreakfastRestriction(int breakfastRestriction) {
        this.breakfastRestriction = breakfastRestriction;
    }

    public void setLunchRestriction(int lunchRestriction) {
        this.lunchRestriction = lunchRestriction;
    }

    public void setDinnerRestriction(int dinnerRestriction) {
        this.dinnerRestriction = dinnerRestriction;
    }
  
    public void setInstantBloodSugar(int instantBloodSugar) {
        this.instantBloodSugar = instantBloodSugar;
    }
    public void setCarbInsulinRatio(int carbInsulinRatio) {
        this.carbInsulinRatio = carbInsulinRatio;
    }

    public void setInsulinSensivity (int insulinSensivity){
        this.insulinSensivity = insulinSensivity;
    }

    public void setFavourites(ArrayList<String> favourites) {
        this.favourites = favourites;
    }

    public void addFoodToFavourites(Food food) {
        food.setFavourite();
        this.favourites.add(food.getName());
    }

    public void removeFoodFromFavourites(Food food) {
        food.removeFavourite();
        this.favourites.remove(food.getName());
    }

    public ArrayList<Food> getAddedFoods (){
        return this.addedFoods;
    }

    public void setAddedFoods (ArrayList<Food> addedFoods){
        this.addedFoods = addedFoods;

    }
    public void addFoodToAddedFoods(Food food){
        this.addedFoods.add(food);

    }

    public int getInstantBloodSugar(){
        return this.instantBloodSugar;

    }

    public int getCarbInsulinRatio(){
        return  this.carbInsulinRatio;

    }

}
