package com.example.insuchef;

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

    public Profile(){}
    //constructors
    public Profile(String name, int weight){
        this.name = name;
        this.weight = weight;
        this.calc = new Calc(weight);
    }

    public Profile(String name, double totalinsulin){
        this.name = name;
        this.calc = new Calc(totalinsulin);
    }

    public Profile(String name, double weight, int targetbloodsugar){
        this.name = name;
        this.weight = weight;
        this.targetBloodSugar = targetbloodsugar;
        this.calc = new Calc(weight, targetbloodsugar);
    }

    public Profile(String name, double weight,int targetbloodsugar, int diabetesAge){
        this.name = name;
        this.weight = weight;
        this.targetBloodSugar = targetbloodsugar;
        this.calc = new Calc(weight, targetbloodsugar,diabetesAge);
    }

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


}
