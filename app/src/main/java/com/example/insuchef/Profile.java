package com.example.insuchef;

// this class holds peoples information in different person objects
// this class can be used when creating the profile page
public class Profile {

    private static int numberOfProfiles;
    private String name;
    //private int age;
    private double weight;
    private int numberOfMeals;
    private int targetBloodSugar;
    private int diabetesAge; // how many years does the patient deal with diabetes
    private Calc calc;


    //constructors
    public Profile(String name, double weight){
        this.name = name;
        this.weight = weight;
        this.calc = new Calc(weight);
    }

    public Profile(String name, double totalinsulin, int numberofmeals){
        this.name = name;
        this.numberOfMeals = numberofmeals;
        this.calc = new Calc(totalinsulin,numberofmeals);
    }

    public Profile(String name, double weight, int numberofmeals, int targetbloodsugar){
        this.name = name;
        this.weight = weight;
        this.numberOfMeals = numberofmeals;
        this.targetBloodSugar = targetbloodsugar;
        this.calc = new Calc(weight, targetbloodsugar, numberofmeals);
    }

    public Profile(String name, double weight, int numberofmeals, int targetbloodsugar, int diabetesAge){
        this.name = name;
        this.weight = weight;
        this.numberOfMeals = numberofmeals;
        this.targetBloodSugar = targetbloodsugar;
        this.calc = new Calc(weight, targetbloodsugar, numberofmeals, diabetesAge);
    }

    // getters
    public static int getNumberOfProfiles(){
        return numberOfProfiles;
    }

    public String getName(){
        return this.name;
    }

    /*public int getAge(){
        return this.age;
    }*/

    public double getWeight(){
        return this.weight;
    }

    public int getnumberOfMeals(){
        return this.numberOfMeals;
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

    //setters

    public static void setNumberOfProfiles(int profilenumber){
        numberOfProfiles = profilenumber;
    }

    public void setName(String name){
        this.name = name;
    }

   /* public void setAge(int age){
        this.age = age;
    }*/

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setnumberOfMeals(int numberofmeals){
        this.numberOfMeals = numberofmeals;
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
}
