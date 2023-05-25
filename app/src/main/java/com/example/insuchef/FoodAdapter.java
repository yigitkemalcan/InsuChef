package com.example.insuchef;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    private ArrayList<Food> foods;
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<EditText> editTexts;


    public FoodAdapter(ArrayList<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
        //this.editTexts = new ArrayList<>();
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
        //editTexts.add(holder.gram);
        //editTextMap.put(food, holder.gram);
    }
    public ArrayList<EditText> getEditTexts() {
        return editTexts;
    }
    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(ArrayList<Food> newArr) {
        this.foods = newArr;
    }

    class FoodHolder extends RecyclerView.ViewHolder{
        TextView name;
        EditText gram;
        ImageButton cancelButton;
        CardView cardView;
        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodName);
            gram = itemView.findViewById(R.id.editTextNumber);
            cardView = itemView.findViewById(R.id.cardView);
            cancelButton = itemView.findViewById(R.id.imageButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.OnItemClick(foods.get(position),position);
                        if(foods.get(position).isLocked()){
                            cardView.setCardBackgroundColor(Color.LTGRAY);
                            gram.setEnabled(false);
                        }
                        else{
                            cardView.setCardBackgroundColor(Color.WHITE);
                            gram.setEnabled(true);
                        }
                        notifyItemChanged(position);
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnButtonCLick(foods.get(position), position);
                        for(int i=0;i<foods.size();i++){
                            gram.setText(String.valueOf(foods.get(i).getGram()) );
                        }
                        notifyDataSetChanged();
                    }
                }

            });
            gram.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnGramTextChanged(foods.get(position), position, s.toString());
                    }
                    //notifyDataSetChanged();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        public void setData(Food food){
            this.name.setText(food.getName());
            if (food.getGram() != -1) {
                this.gram.setText(String.valueOf(food.getGram()));
            } else {
                this.gram.setText("");
            }
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
            if(food.getGram()!=-1 && food.getGram()!=0){
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
    public ArrayList<Food> getFoodsExcept(int i){
        ArrayList<Food> newArr = new ArrayList<>();
        for(int j=0;j<foods.size();j++){
            if(j!=i){
                newArr.add(foods.get(j));
            }
        }
        return newArr;
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
    public ArrayList<Food> getLockedGramFoods(){
        ArrayList<Food> lockedGramFoods = new ArrayList<>();
        for(Food food: foods){
            if(food.isLocked()){
                lockedGramFoods.add(food);
            }
        }
        return lockedGramFoods;
    }
    public ArrayList<Food> getFullGramFoods(){
        ArrayList<Food> fullGramFoods = new ArrayList<>();
        for(Food food: foods){
            if(food.getGram()!=-1){
                fullGramFoods.add(food);
            }
        }
        return fullGramFoods;
    }

    public void distributeExcept(int carbRestriction,Food food){
        //kilitli olmayanlar arasında food dışındakilere total kho dağıt
        ArrayList<Food> unlockedFoods = getUnlockedFoods();
        unlockedFoods.remove(food);
        distributeFoods(unlockedFoods,(int)(carbRestriction-Calc.calculateCarbs(getLockedGramFoods())));

    }
    public void distributeExcept(int carbRestriction){
        //kilitli olmayanlar arasında total kho dağıt
        ArrayList<Food> unlockedFoods = getUnlockedFoods();
        distributeFoods(unlockedFoods,(int)(carbRestriction-Calc.calculateCarbs(getLockedGramFoods())));

    }
    public void distributeNulls(int carbRestriction){
        //yalnızca boş olanlar arasında total kho dağıt
        ArrayList<Food> nullGramFoods = getNullGramFoods();
        ArrayList<Food> fullGramFoods = getFullGramFoods();
        distributeFoods(nullGramFoods,(int) (carbRestriction-Calc.calculateCarbs(fullGramFoods)));

    }
    public void distributeProportional(int carbRestriction,EditText editText){
        String str =editText.getText().toString();
        double carbs;
        if(!str.equals("")){
            carbs = Double.valueOf(str);
        }
        else{
            carbs = 0;
        }
        double ratio = carbs/carbRestriction;
        ArrayList<Double> carbAmountsRespectToGram = new ArrayList<>();
        if(carbs!=carbRestriction){
            for(int i=0;i<foods.size();i++){
                carbAmountsRespectToGram.add(foods.get(i).getCarbAmountRespectToGram());
            }
            for(int i=0;i<foods.size();i++){
                foods.get(i).setGram((int)(carbAmountsRespectToGram.get(i)*ratio*100/foods.get(i).getCarbAmount()));
                System.out.println(foods.get(i).getGram());
            }
        }
        //updateEditTexts();
    }
    public void distributeFoods(ArrayList<Food> foods,int carbCount){
        double eachCarbAmount = (double) carbCount/foods.size();
        for(int i=0;i<foods.size();i++){
            foods.get(i).setGram((int)(eachCarbAmount*100/foods.get(i).getCarbAmount()));
            //updateEditTexts();
            System.out.println((int)(eachCarbAmount*100/foods.get(i).getCarbAmount()));
        }
    }
    public void distributeFoods(int carbCount){
        distributeFoods(foods,carbCount);
    }
    public void updateEditTexts() {
        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            EditText editText = editTexts.get(i);
            if (food.getGram() != -1) {
                editText.setText(String.valueOf(food.getGram()));
            } else {
                editText.setText("");
            }
        }
    }

}
