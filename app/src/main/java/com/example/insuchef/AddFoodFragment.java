package com.example.insuchef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFoodFragment newInstance(String param1, String param2) {
        AddFoodFragment fragment = new AddFoodFragment();
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

    ProfileManager profileManager;
    Profile profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        profileManager = ProfileManager.getInstance(requireContext());
        profile = profileManager.getProfile();

        EditText foodNameEdTxt = view.findViewById(R.id.foodNameEdTxt);
        EditText carbPerEdTxt = view.findViewById(R.id.carbPerEdTxt);
        EditText fatPerEdTxt = view.findViewById(R.id.fatPerEdTxt);

        Button addNewFood = view.findViewById(R.id.addFoodButton);
        addNewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(foodNameEdTxt.getText().toString()) || TextUtils.isEmpty(carbPerEdTxt.getText().toString()) || TextUtils.isEmpty(fatPerEdTxt.getText().toString())){
                    Toast.makeText(getContext(), "Information is missing", Toast.LENGTH_SHORT).show();
                }
                else {
                        Food food = new Food();
                        food.setName(foodNameEdTxt.getText().toString());
                        food.setCarbAmount(Double.parseDouble(carbPerEdTxt.getText().toString()));
                        food.setFatAmount(Double.parseDouble(fatPerEdTxt.getText().toString()));
                        profile.addFoodToAddedFoods(food);
                        profileManager.saveProfile(profile);
                        MainPage.foodList.foods.add(food);
                        Toast.makeText(getContext(), "Food added", Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;
    }
}