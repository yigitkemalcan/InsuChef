package com.example.insuchef;

import android.content.Context;
import android.content.SharedPreferences;

public class ProfileManager {
    private static final String PREF_NAME = "ProfilePrefs";
    private static final String KEY_WEIGHT = "weight";
    private static final String INSULIN_SENSIVITY = "insulinSensivity";
    private static final String KEY_BREAKFAST_RESTRICTION = "breakfastRestriction";
    private static final String KEY_LUNCH_RESTRICTION = "lunchRestriction";
    private static final String KEY_DINNER_RESTRICTION = "dinnerRestriction";
    private static final String KEY_TARGET_BLOOD_SUGAR = "targetBloodSugar";
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
        editor.apply();
    }

    public Profile getProfile() {
        double weight = sharedPreferences.getFloat(KEY_WEIGHT, 0);
        int insulinSensivity = sharedPreferences.getInt(INSULIN_SENSIVITY, 0);
        int breakfastRestriction = sharedPreferences.getInt(KEY_BREAKFAST_RESTRICTION, 0);
        int lunchRestriction = sharedPreferences.getInt(KEY_LUNCH_RESTRICTION, 0);
        int dinnerRestriction = sharedPreferences.getInt(KEY_DINNER_RESTRICTION, 0);
        int targetBloodSugar = sharedPreferences.getInt(KEY_TARGET_BLOOD_SUGAR, 0);

        Profile profile = new Profile();
        profile.setWeight(weight);
        profile.setInsulinSensivity(insulinSensivity);
        profile.setBreakfastRestriction(breakfastRestriction);
        profile.setLunchRestriction(lunchRestriction);
        profile.setDinnerRestriction(dinnerRestriction);
        profile.setTargetBloodSugar(targetBloodSugar);

        return profile;
    }
}

