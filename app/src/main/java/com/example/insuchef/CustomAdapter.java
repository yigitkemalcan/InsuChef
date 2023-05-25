package com.example.insuchef;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Food> {

    private Context mContext;
    private int mResource;
    private MealSelectionFragment mealSelect;
    private ArrayList<Food> original;
    private ArrayList<Food> filtered;

    public CustomAdapter(Context context, int resource, MealSelectionFragment frag, List<Food> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mealSelect = frag;
        original = new ArrayList<>(objects);
        filtered = new ArrayList<>(objects);

    }

    public void filterSearch(String search) {
        filtered.clear();

        if (search.isEmpty())
        {
            filtered.addAll(original);
        }
        else
        {
            String constraint = search.toLowerCase();

            for (Food food : original)
            {
                if (food.getName().toLowerCase().contains(constraint))
                {
                    filtered.add(food);
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        Food food = getItem(position);

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        if (food != null) {

            TextView text = view.findViewById(R.id.customListLayout);
            text.setText(food.toString());
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

        return view;
    }

    @Override
    public int getCount() {
        return filtered.size();
    }

    @Override
    public Food getItem(int position) {
        return filtered.get(position);
    }

    /* public Filter getFilter() {

    } */

    /* private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<Food> filtered = new ArrayList<>();
            FilterResults filterResult = new FilterResults();

            if (charSequence != null && charSequence.length() > 0)
            {
                String constraint = charSequence.toString().toLowerCase();
                ,
                for (Food food : tempArr)
                {
                    if (food.getName().toLowerCase().contains(constraint))
                    {
                        Food copyFood = food.copy();
                        filtered.add(copyFood);
                    }
                }

                filterResult.count = filtered.size();
                filterResult.values = filtered;

            }
            else
            {
                filterResult.count = tempArr.size();
                filterResult.values = tempArr;
            }

            return filterResult;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        } */
}

