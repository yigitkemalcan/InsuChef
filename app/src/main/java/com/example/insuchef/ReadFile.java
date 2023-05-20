package com.example.insuchef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class ReadFile {
    protected static File file;
    protected static JSONArray array;

    public ReadFile() throws IOException, JSONException {
        file = new File("food.json");

        BufferedReader buff = new BufferedReader(new FileReader(file.getPath()));
        StringBuilder sb = new StringBuilder();
        String c;
        while((c = buff.readLine()) != null)
        {
            sb.append(c);
        }
        JSONTokener token = new JSONTokener(sb.toString());
        array = new JSONArray(token);

    }

    public JSONObject getFood(String name) throws JSONException {
        JSONArray j = (JSONArray)array.get(0);
        for(int i = 0; i < array.length(); i++)
        {
            for(int k = 0; k < ((JSONArray) array.get(i)).length();k++)
            {
                j = (JSONArray) array.get(i);
                if(((JSONObject)(j.get(k))).getString("description").equals(name))
                {
                    return (JSONObject)j.get(k);
                }
            }
        }
        return (JSONObject)j.get(0);
    }

    public ArrayList<String> getFoodList() throws JSONException {
        ArrayList<String> list = new ArrayList<String>();

        JSONArray j = (JSONArray)array.get(0);
        for(int i = 0; i < array.length(); i++)
        {
            for(int k = 0; k < ((JSONArray) array.get(i)).length();k++)
            {
                j = (JSONArray) array.get(i);
                list.add(((JSONObject)(j.get(k))).getString("description"));
            }
        }
        return list;
    }

    public String getName(Object obj) throws JSONException {
        return ((JSONObject)obj).getString("description");
    }

    public double getCarb(Object obj) throws JSONException {
        JSONArray ar = ((JSONObject)obj).getJSONArray("foodNutrients");
        JSONArray arr = new JSONArray(ar.toString());
        for(int k = 0; k < arr.length(); k++)
        {
            if(((JSONObject)arr.get(k)).getString("name").equals("Carbohydrate, by difference"))
            {
                return ((JSONObject)arr.get(k)).getDouble("amount");
            }
        }
        return 0;
    }

    public double getProtein(Object obj) throws JSONException {
        JSONArray ar = ((JSONObject)obj).getJSONArray("foodNutrients");
        JSONArray arr = new JSONArray(ar.toString());
        // JSONArray arr = new JSONArray(((JSONObject)obj).getJSONArray("foodNutrients"));
        for(int k = 0; k < arr.length(); k++)
        {
            if(((JSONObject)arr.get(k)).getString("name").equals("Protein"))
            {
                return ((JSONObject)arr.get(k)).getDouble("amount");
            }
        }
        return 0;
    }

    public double getFat(Object obj) throws JSONException {
        JSONArray ar = ((JSONObject)obj).getJSONArray("foodNutrients");
        JSONArray arr = new JSONArray(ar.toString());
        //JSONArray arr = new JSONArray(((JSONObject)obj).getJSONArray("foodNutrients"));
        for(int k = 0; k < arr.length(); k++)
        {
            if(((JSONObject)arr.get(k)).getString("name").equals("Total lipid (fat)"))
            {
                return ((JSONObject)arr.get(k)).getDouble("amount");
            }
        }
        return 0;
    }

    public double getCalories(Object obj) throws JSONException {
        double energy = 0;
        JSONArray ar = ((JSONObject)obj).getJSONArray("foodNutrients");
        JSONArray arr = new JSONArray(ar.toString());
        // JSONArray arr = new JSONArray(((JSONObject)obj).getJSONArray("foodNutrients"));
        for(int k = 0; k < arr.length(); k++)
        {
            if(((JSONObject)arr.get(k)).getString("name").equals("Energy (Atwater General Factors)"))
            {
                energy = ((JSONObject)arr.get(k)).getDouble("amount");
            }
        }
        if(energy == 0)
        {
            double carb = getCarb(obj);
            double fat = getFat(obj);
            double protein = getProtein(obj);
            energy = (carb * 4) + (fat * 9) + (protein * 4);
        }
        return energy;
    }
}


