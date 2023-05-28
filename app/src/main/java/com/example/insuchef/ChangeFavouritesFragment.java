package com.example.insuchef;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeFavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeFavouritesFragment extends Fragment {

    ArrayList<Food> list;
    Food selectedFood;
    Button addButton;
    Button deleteButton;
    Profile profile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangeFavouritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangeFavouritesFragment newInstance(String param1, String param2) {
        ChangeFavouritesFragment fragment = new ChangeFavouritesFragment();
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

        list = new ArrayList<>(MainPage.foodList.foods);
        Collections.sort(list, new Comparator<Food>() {
            @Override
            public int compare(Food f1, Food f2) {
                return Boolean.compare(f2.isFavourite(), f1.isFavourite());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        SearchView searchBar = view.findViewById(R.id.favouritesSearchBar);
        ListView foodListView = view.findViewById(R.id.favouritesFoodList);

        FavouritesAdapter favAdapter = new FavouritesAdapter(this.getActivity(), R.layout.custom_list_layout, list);
        foodListView.setAdapter(favAdapter);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                favAdapter.filterSearch(newText);
                return true;
            }
        });

        addButton = view.findViewById(R.id.addFav);
        deleteButton = view.findViewById(R.id.deleteFav);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFood != null && !selectedFood.isFavourite())
                {
                    ProfileManager pm = ProfileManager.getInstance(requireContext());
                    profile = pm.getProfile();
                    profile.addFoodToFavourites(selectedFood);
                    pm.saveProfile(profile);
                    favAdapter.notifyDataSetChanged();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFood != null && selectedFood.isFavourite())
                {
                    ProfileManager pm = ProfileManager.getInstance(requireContext());
                    profile = pm.getProfile();
                    profile.removeFoodFromFavourites(selectedFood);
                    pm.saveProfile(profile);
                    favAdapter.notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    private class FavouritesAdapter extends CustomAdapter {

        private FavouritesAdapter(Context context, int resource, List<Food> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            Food food = getItem(position);
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.star_icon, null);


            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(mResource, parent, false);
            }

            TextView text = view.findViewById(R.id.customListLayout);

            if (food != null) {
                text.setText(food.toString());
            }

            if (food.isFavourite()) {
                text.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            }
            else {
                text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }

            if (selectedFood != null)
            {
                if (food == selectedFood)
                {
                    view.setBackgroundResource(R.drawable.favourite_border);
                }
                else
                {
                    view.setBackground(null);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFood = food;
                    notifyDataSetChanged();
                }
            });

            return view;
        }

    }
}