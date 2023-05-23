package com.example.insuchef;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealSelectionFragment extends Fragment {

    File jFile;
    ArrayList<Food> selectedMeal;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MealSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mealSelectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealSelectionFragment newInstance(String param1, String param2) {
        MealSelectionFragment fragment = new MealSelectionFragment();
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

        selectedMeal = new ArrayList<>();

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_selection, container, false);

        SearchView searchBar = view.findViewById(R.id.searchBar);
        ListView foodListView = view.findViewById(R.id.foodList);

        CustomAdapter arrayAdapter = new CustomAdapter(this.getActivity(), R.layout.custom_list_layout, MainPage.foodList.foods);
        foodListView.setAdapter(arrayAdapter);

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Food food = (Food) foodListView.getItemAtPosition(position);

                if (food.isSelected()) {
                    selectedMeal.add(food);
                }
                else {
                    selectedMeal.remove(food);
                }
            }
        });

        Button distribution = view.findViewById(R.id.distribution);
        distribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DistributionFragment dist = new DistributionFragment();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, dist);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

}