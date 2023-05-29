package com.example.insuchef;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealSelectionFragment extends Fragment {


    ArrayList<Food> selectedMeal;
    ArrayList<Food> foodList;


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
        foodList = new ArrayList<>(MainPage.foodList.foods);

        // Reset all food selection
        for (Food f : foodList)
        {
            f.removeSelected();
        }

        // Sort by favourites
        Collections.sort(foodList, new Comparator<Food>() {
            @Override
            public int compare(Food f1, Food f2) {
                return Boolean.compare(f2.isFavourite(), f1.isFavourite());
            }
        });

    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_selection, container, false);

        SearchView searchBar = view.findViewById(R.id.searchBar);
        ListView foodListView = view.findViewById(R.id.foodListView);

        MealSelectionAdapter arrayAdapter = new MealSelectionAdapter(this.getActivity(), R.layout.custom_row_meal_selection, foodList, this);
        foodListView.setAdapter(arrayAdapter);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.filterSearch(newText);
                return true;
            }
        });


        Button distribution = view.findViewById(R.id.distribution);
        distribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                // Bundle the selected foods
                DistributionFragment dist = new DistributionFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("meal", selectedMeal);
                dist.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, dist);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private class MealSelectionAdapter extends CustomAdapter {

        private MealSelectionFragment mealSelect;

        private MealSelectionAdapter(Context context, int resource, List<Food> objects, MealSelectionFragment frag)
        {
            super(context, resource, objects);
            mealSelect = frag;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.star_icon, null);
            Food food = getItem(position);

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(mResource, parent, false);
            }


            TextView text = view.findViewById(R.id.textMealSelect);
            ImageButton infoButton = view.findViewById(R.id.infoButton);

            if (food != null) {
                text.setText(food.getName());
            }

            if (food.isFavourite()) {
                text.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            }
            else
            {
                text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            if(food.isSelected()) {
                view.setBackgroundResource(R.drawable.item_background);
            }
            else {
                view.setBackground(null);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    food.toggleSelected();

                    if (food.isSelected())
                    {
                        food.addToMeal(mealSelect);
                    }
                    else
                    {
                        food.removeFromMeal(mealSelect);
                    }

                    TextView selectionCount = mealSelect.getView().findViewById(R.id.selectionText);

                    if (mealSelect.selectedMeal.size() != 0)
                    {
                        String s = "Food Selected: " + mealSelect.selectedMeal.size();
                        selectionCount.setText(s);
                    }
                    else
                    {
                        selectionCount.setText("");
                    }

                    notifyDataSetChanged();
                }
            });

            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    dialogBuilder.setTitle("Food Information");
                    dialogBuilder.setMessage(food.toString());
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    dialog.show();
                }
            });

            return view;
        }

    }

}