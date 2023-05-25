package com.example.insuchef;

import android.widget.Toast;

import java.util.ArrayList;

// this class is for all insulin calculation methods
public class Calc{

    private double basalInsulin;
    private double bolusInsulin;
    private double totalInsulin;
    private double totalBolus;
    private double isf;// ısf for insulin sensitization factor
    private double ratio; // carb/insulin ratio

    private double weight;
    private int bloodSugar;
    private int targetBloodSugar;
    private double carbCount;
    private int totalNumberOfMeals;
    private int count;
    private int diabetesAge;
    private ArrayList<Food> foods;

    public static double calculateCarbs(ArrayList<Food> list){
        double carbs =0;
        for(Food food: list){
            carbs+=food.getCarbAmountRespectToGram();
        }
        return carbs;
    }
    public Calc(double totalinsulin){
        this.totalInsulin = totalinsulin;
        this.totalNumberOfMeals = 3;
        this.diabetesAge = 10;
        this.targetBloodSugar = 120;
        this.isf = calculateIsf();
        this.ratio = calculateRatio();
    }

    public Calc(double totalinsulin, int targetbloodsugar, int isf, int ratio){
        this.totalInsulin = totalinsulin;
        this.targetBloodSugar = targetbloodsugar;
        this.isf = isf;
        this.ratio = ratio;
    }

    public Calc(int weight){
        this.weight = weight;
        this.totalInsulin = this.calculateTotal(this.weight);
        this.totalNumberOfMeals = 3;
        this.diabetesAge = 10;
        this.targetBloodSugar = 120;
        this.isf = calculateIsf();
        this.ratio = calculateRatio();
    }

    public Calc(double weight,int targetbloodsugar){
        this.weight = weight;
        this.targetBloodSugar = targetbloodsugar;
        this.totalInsulin = this.calculateTotal(this.weight);
        this.diabetesAge = 10;
        this.isf = calculateIsf();
        this.ratio = calculateRatio();
    }

    public Calc(double weight, int targetbloodsugar,int diabetesage){
        this.weight = weight;
        this.targetBloodSugar = targetbloodsugar;
        this.totalInsulin = this.calculateTotal(this.weight);
        this.diabetesAge = diabetesage;
        this.isf = calculateIsf();
        this.ratio = calculateRatio();
    }

    // getters
    public double getBasal(){
        return this.basalInsulin;
    }

    public double getBolus(){
        return this.bolusInsulin;
    }

    public double getTotal(){
        return this.totalInsulin;
    }

    public double getIsf(){
        return this.isf;
    }

    public double getRatio(){
        return this.ratio;
    }

    //setters

    public void setBloodSugar(int bloodsugar){
        this.bloodSugar = bloodsugar;
    }

    public void setTargetBloodSugar(int targetbloodsugar){
        this.targetBloodSugar = targetbloodsugar;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setCarbCount(double carbcount){
        this.carbCount = carbcount;
    }

    public void setTotalNumberofMeals(int totalnumberofmeals){
        this.totalNumberOfMeals = totalnumberofmeals;
    }


    //methods for calculation

    public double calculateIsf(){
        return (1800 / this.totalInsulin);
    }

    public double calculateRatio(){
        return (500 / this.totalInsulin);
    }

    public double calculateBasal(){
        return (this.totalInsulin - this.totalBolus);
    }

    // calculating the totalbolus can change
    public double calculateBolus(){
        this.bolusInsulin = (Math.abs(this.targetBloodSugar - this.bloodSugar) / this.isf) + ((this.carbCount) / (this.ratio));
        this.count++;
        if(count <= totalNumberOfMeals)
        {
            this.totalBolus = this.totalBolus + this.bolusInsulin;
        }
        else
        {
            count = 1;
            this.totalBolus = 0;
            this.totalBolus = this.totalBolus + this.bolusInsulin;
        }
        if(diabetesAge < 10)
        {
            return (this.bolusInsulin * 0.9); // reduce the insulin by  % 10.
        }
        return this.bolusInsulin;
    }

    public double calculateTotal(double weight){
        this.totalInsulin = (weight * 0.55);
        if(diabetesAge < 10)
        {
            return (this.totalInsulin * 0.9); // reduce the insulin by  % 10.
        }
        return this.totalInsulin;
    }
}