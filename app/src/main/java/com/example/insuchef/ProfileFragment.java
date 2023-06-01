package com.example.insuchef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProfileManager profileManager;
    private Profile profile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileManager = ProfileManager.getInstance(requireContext());
        profile = profileManager.getProfile();

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button favorites = view.findViewById(R.id.favorites);
        EditText breakfast = view.findViewById(R.id.breakfastRestriction);
        EditText lunch = view.findViewById(R.id.lunchRestriction);
        EditText dinner = view.findViewById(R.id.dinnerRestriction);
        EditText targetBloodSugar = view.findViewById(R.id.targetBloodSugarEdTxt);
        EditText insulinSensitivityFactor = view.findViewById(R.id.insulinSensivityFactorEdTxt);
        EditText weight = view.findViewById(R.id.weightEdTxt);

        breakfast.setText(String.valueOf(profile.getBreakfastRestriction()));
        lunch.setText(String.valueOf(profile.getLunchRestriction()));
        dinner.setText(String.valueOf(profile.getDinnerRestriction()));
        targetBloodSugar.setText(String.valueOf(profile.getTargetBloodSugar()));
        insulinSensitivityFactor.setText(String.valueOf(profile.getInsulinSensivity()));
        weight.setText(String.valueOf(profile.getWeight()) );

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new ChangeFavouritesFragment());
                fragmentTransaction.commit();
            }
        });
        Button showFavorites = view.findViewById(R.id.showFavorites);
        showFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new ShowFavoritesFragment());
                fragmentTransaction.commit();
            }
        });
        Button addFood = view.findViewById(R.id.addFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new AddFoodFragment());
                fragmentTransaction.commit();
            }
        });
        Button updateButton = view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!breakfast.getText().toString().equals("") && Integer.parseInt(breakfast.getText().toString()) < -1 || !lunch.getText().toString().equals("") && Integer.parseInt(lunch.getText().toString()) < -1 || !dinner.getText().toString().equals("") && Integer.parseInt(dinner.getText().toString()) < -1 || !targetBloodSugar.getText().toString().equals("") && Integer.parseInt(targetBloodSugar.getText().toString()) < 0 || !insulinSensitivityFactor.getText().toString().equals("") && Integer.parseInt(insulinSensitivityFactor.getText().toString()) < 0 || !weight.getText().toString().equals("") && Float.parseFloat(weight.getText().toString()) < 0 ){
                    Toast.makeText(getContext(),"Information is wrong!", Toast.LENGTH_SHORT).show();
                }
                else{
                if (!TextUtils.isEmpty(weight.getText().toString())){
                    profile.setWeight(Float.parseFloat(weight.getText().toString()));
                }
                else {}
                if (!TextUtils.isEmpty(breakfast.getText().toString())) {
                    profile.setBreakfastRestriction(Integer.parseInt(breakfast.getText().toString()));
                } else {
                    profile.setBreakfastRestriction(-1);
                }
                if (!TextUtils.isEmpty(lunch.getText().toString())) {
                    profile.setLunchRestriction(Integer.parseInt(lunch.getText().toString()));
                } else {
                    profile.setLunchRestriction(-1);
                }
                if (!TextUtils.isEmpty(dinner.getText().toString())) {
                    profile.setDinnerRestriction(Integer.parseInt(dinner.getText().toString()));
                } else {
                    profile.setDinnerRestriction(-1);
                }
                if (!TextUtils.isEmpty(targetBloodSugar.getText().toString())){
                    profile.setTargetBloodSugar(Integer.parseInt(targetBloodSugar.getText().toString()));
                }
                else {}
                if (!TextUtils.isEmpty(insulinSensitivityFactor.getText().toString())){
                    profile.setInsulinSensivity(Integer.parseInt(insulinSensitivityFactor.getText().toString()));
                }
                else {}
                //TODO: Set also insulin sensitivity factor and we have to take different isf for 3 meals at the beginning
                profileManager.saveProfile(profile);
                Toast.makeText(getContext(),"Updated!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}