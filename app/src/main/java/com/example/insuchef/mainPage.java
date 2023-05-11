package com.example.insuchef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.insuchef.databinding.ActivityMainBinding;

public class mainPage extends AppCompatActivity {



    FragmentContainerView fragmentContainer;
    Fragment mainFragment;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new mainFragment());
        binding.navigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                replaceFragment(new mainFragment());
            }
            else if (item.getItemId() == R.id.profile){
                replaceFragment(new profileFragment());
            }
            else {
                replaceFragment(new mainFragment());
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