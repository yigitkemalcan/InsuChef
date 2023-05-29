package com.example.insuchef;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Food> {

    protected Context mContext;
    protected int mResource;
    protected ArrayList<Food> original;
    protected ArrayList<Food> filtered;

    public CustomAdapter(Context context, int resource, List<Food> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
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
    public int getCount() {
        return filtered.size();
    }

    @Override
    public Food getItem(int position) {
        return filtered.get(position);
    }


}

