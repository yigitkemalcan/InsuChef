package com.example.insuchef;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class FoodList {

    public ArrayList<Food> foods;

    FoodList(File file) throws Exception {

        this.foods = new ArrayList<>();

        ReadFile read = new ReadFile(file);

        JSONArray jsonArr = read.array;

        for (int i = 0; i < jsonArr.length(); i++) {

            JSONArray objArr = (JSONArray) jsonArr.get(i);

            for (int j = 0; j < objArr.length(); j++) {

                JSONObject jsonObj = (JSONObject) objArr.get(j);

                String name = read.getName(jsonObj);
                double carb = read.getCarb(jsonObj);
                double protein = read.getProtein(jsonObj);
                double fat = read.getFat(jsonObj);
                double calories = read.getCalories(jsonObj);

                Food food = new Food(name, carb, protein, fat, calories);

                this.foods.add(food);

            }

        }

        System.out.println("Selam");

    }

}