package com.example.insuchef;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileManager {
    private static final String PREF_NAME = "ProfilePrefs";
    private static final String KEY_WEIGHT = "weight";
    private static final String INSULIN_SENSIVITY = "insulinSensivity";
    private static final String KEY_BREAKFAST_RESTRICTION = "breakfastRestriction";
    private static final String KEY_LUNCH_RESTRICTION = "lunchRestriction";
    private static final String KEY_DINNER_RESTRICTION = "dinnerRestriction";
    private static final String KEY_TARGET_BLOOD_SUGAR = "targetBloodSugar";
    private static final String KEY_FAVOURITES = "favourites";

    private SharedPreferences sharedPreferences;

    private ProfileManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static ProfileManager getInstance(Context context) {
        return new ProfileManager(context.getApplicationContext());
    }

    public void saveProfile(Profile profile) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(INSULIN_SENSIVITY, profile.getInsulinSensivity());
        editor.putFloat(KEY_WEIGHT, (float) profile.getWeight());
        editor.putInt(KEY_BREAKFAST_RESTRICTION, profile.getBreakfastRestriction());
        if(profile.getBreakfastRestriction()!=0){
            editor.putInt(KEY_LUNCH_RESTRICTION, profile.getLunchRestriction());
        }
        if(profile.getLunchRestriction()!=0) {
            editor.putInt(KEY_DINNER_RESTRICTION, profile.getDinnerRestriction());
        }
        if(profile.getDinnerRestriction()!=0) {
            editor.putInt(KEY_TARGET_BLOOD_SUGAR, profile.getTargetBloodSugar());
        }

        // Store the favourites String ArrayList by using the Gson library
        Gson gson = new Gson();
        String json = gson.toJson(profile.getFavourites());
        editor.putString(KEY_FAVOURITES, json);

        editor.apply();
    }

    public Profile getProfile() {
        double weight = sharedPreferences.getFloat(KEY_WEIGHT, 0);
        int insulinSensivity = sharedPreferences.getInt(INSULIN_SENSIVITY, 0);
        int breakfastRestriction = sharedPreferences.getInt(KEY_BREAKFAST_RESTRICTION, 0);
        int lunchRestriction = sharedPreferences.getInt(KEY_LUNCH_RESTRICTION, 0);
        int dinnerRestriction = sharedPreferences.getInt(KEY_DINNER_RESTRICTION, 0);
        int targetBloodSugar = sharedPreferences.getInt(KEY_TARGET_BLOOD_SUGAR, 0);
        String json = sharedPreferences.getString(KEY_FAVOURITES, "");
        ArrayList<String> favourites;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        if (json != null && !json.equals("")) {
            favourites = gson.fromJson(json, type);
        }
        else
        {
            favourites = new ArrayList<>();
        }

        Profile profile = new Profile();
        profile.setWeight(weight);
        profile.setInsulinSensivity(insulinSensivity);
        profile.setBreakfastRestriction(breakfastRestriction);
        profile.setLunchRestriction(lunchRestriction);
        profile.setDinnerRestriction(dinnerRestriction);
        profile.setTargetBloodSugar(targetBloodSugar);
        profile.setFavourites(favourites);

        return profile;
    }
}

