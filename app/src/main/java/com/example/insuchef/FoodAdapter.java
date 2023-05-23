package com.example.insuchef;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    private ArrayList<Food> foods;
    private Context context;
    private OnItemClickListener listener;

    public FoodAdapter(ArrayList<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        Food food = foods.get(position);
        holder.setData(food);

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        TextView name;
        EditText gram;
        ImageButton cancelButton;
        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodName);
            gram = itemView.findViewById(R.id.editTextNumber);
            cancelButton = itemView.findViewById(R.id.imageButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(foods.get(position),position);
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnButtonCLick(foods.get(position), position);
                    }
                }
            });
            gram.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnGramTextChanged(foods.get(position), position, s.toString());
                    }
                }
            });
        }
        public void setData(Food food){
            this.name.setText(food.getName());

        }
    }
    public interface OnItemClickListener{
        void OnItemClick(Food food, int position);
        void OnButtonCLick(Food food, int position);
        void OnGramTextChanged(Food food, int position, String gram);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void removeItem(int position) {
        if (position >= 0 && position < foods.size()) {
            foods.remove(position);
            notifyItemRemoved(position);
        }
    }
    public boolean isAllGramInfoNull(){
        for(Food food: foods){
            if(food.getGram()!=-1){
                return false;
            }
        }
        return true;
    }
    public boolean isAllGramInfoFull(){
        for(Food food: foods){
            if(food.getGram()==-1){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Food> getUnlockedFoods(){
        ArrayList<Food> unlockedFoods = new ArrayList<>();
        for(Food food: foods){
            if(!food.isLocked()){
                unlockedFoods.add(food);
            }
        }
        return unlockedFoods;
    }
    public ArrayList<Food> getNullGramFoods(){
        ArrayList<Food> nullGramFoods = new ArrayList<>();
        for(Food food: foods){
            if(food.getGram()==-1){
                nullGramFoods.add(food);
            }
        }
        return nullGramFoods;
    }

    public void distribute(int carbRestriction){
        //kilitli olmayanlar arasında total kho dağıt
        ArrayList<Food> unlockedFoods = getUnlockedFoods();

    }
    public void distributeNulls(int carbRestriction){
        //yalnızca boş olanlar arasında total kho dağıt
        ArrayList<Food> nullGramFoods = getNullGramFoods();
    }
    public void distributeProportional(int carbRestriction){

    }

}
