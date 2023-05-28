package com.example.insuchef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.insuchef.databinding.ActivityMainBinding;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {


    FragmentContainerView fragmentContainer;
    GetData get;
    public static FoodList foodList;

    ActivityMainBinding binding;
    private ProfileManager profileManager;
    private Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileManager = ProfileManager.getInstance(this);
        profile = profileManager.getProfile();
        ArrayList<Food> added = profile.getAddedFoods();
        for (Food f : added){
            foodList.foods.add(f);
        }
        File jFile = new File(getFilesDir(), "food.json");

        MainFragment mainFrag = new MainFragment();

        replaceFragment(mainFrag);
        binding.navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                replaceFragment(mainFrag);
            }
            else if (item.getItemId() == R.id.profile){
                replaceFragment(new ProfileFragment());
            }
            else {
                replaceFragment(mainFrag);
            }
            return true;
        });



    }

    public void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,fragment);
        fragmentTransaction.commit();
    }
}