package com.example.insuchef;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowFavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowFavoritesFragment extends Fragment {

    ArrayList<Food> favourites;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShowFavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment showFavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowFavoritesFragment newInstance(String param1, String param2) {
        ShowFavoritesFragment fragment = new ShowFavoritesFragment();
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

        favourites = new ArrayList<>();

        for (Food f : MainPage.foodList.getFoods()) {
            if (f.isFavourite()) {
                favourites.add(f);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_favorites, container, false);

        ListView foodListView = view.findViewById(R.id.showFavList);
        ShowFavouritesAdapter showFavAdapter = new ShowFavouritesAdapter(this.getActivity(), R.layout.custom_list_layout, favourites);
        foodListView.setAdapter(showFavAdapter);

        Button change = view.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new ChangeFavouritesFragment());
                fragmentTransaction.commit();
            }
        });

        return view;

    }

    private class ShowFavouritesAdapter extends CustomAdapter {

        private ShowFavouritesAdapter(Context context, int resource, List<Food> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;
            Food food = getItem(position);
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.star_icon, null);

            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(mResource, parent, false);
            }

            TextView text = view.findViewById(R.id.customListLayout);

            if (food != null) {
                text.setText(food.getName());
                text.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            }

            return view;
        }

    }

}
