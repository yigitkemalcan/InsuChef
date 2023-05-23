package com.example.insuchef;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Food> {

    private Context mContext;
    private int mResource;

    public CustomAdapter(Context context, int resource, List<Food> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

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
                notifyDataSetChanged();
            }
        });


    return view;
    }
}
