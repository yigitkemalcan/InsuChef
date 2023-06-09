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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {
    private ArrayList<Food> foods;
    private Context context;
    private OnItemClickListener listener;
    private ArrayList<EditText> editTexts;
    private boolean isListenerActive = true;

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setListenerActive(boolean active) {
        isListenerActive = active;
    }
    public boolean getListenerActive() {
        return isListenerActive;
    }

    public FoodAdapter(ArrayList<Food> foods, Context context) {
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.distribution_item,parent,false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        Food food = foods.get(position);
        holder.setData(food);
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
        TextView test;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.foodName);
            gram = itemView.findViewById(R.id.editTextNumber);
            cardView = itemView.findViewById(R.id.cardView);
            cancelButton = itemView.findViewById(R.id.imageButton);
            test = itemView.findViewById(R.id.grText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null  && position != RecyclerView.NO_POSITION){
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
                            //test.setText(String.valueOf(foods.get(i).getCarbAmountRespectToGram()));
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
                        int is = listener.OnGramTextChanged(foods.get(position), position, s.toString());
                        if(is==0){
                            gram.setText(String.valueOf(foods.get(position).getGram()));
                            gram.setSelection(gram.getText().length());

                        }
                        else if(is==1){
                            //notifyDataSetChanged();
                        }
                    }
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
                double num = food.getCarbAmountRespectToGram();

                DecimalFormat df = new DecimalFormat("#.##");
                this.test.setText("gr ("+String.valueOf(df.format(num)+" cho)"));
            } else {
                this.gram.setText("");
                this.test.setText("gr (0 cho)");
            }
            this.gram.setSelection(gram.getText().length());
        }

    }
    public interface OnItemClickListener{
        void OnItemClick(Food food, int position);
        void OnButtonCLick(Food food, int position);
        int OnGramTextChanged(Food food, int position, String gram);

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
        for(Food food: getUnlockedFoods()){
            if(food.getGram()==-1||food.getGram()==0){
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

    /*public void distributeExcept(int carbRestriction,Food food){
        //kilitli olmayanlar arasında food dışındakilere total kho dağıt
        food.setLocked(true);
        ArrayList<Food> unlockedFoods = getUnlockedFoods();
        //Toast.makeText(context.getApplicationContext(), String.valueOf(carbRestriction-Calc.calculateCarbs(getLockedGramFoods())),Toast.LENGTH_SHORT).show();
        distributeFoods(unlockedFoods,(int)(carbRestriction-Calc.calculateCarbs(getLockedGramFoods())));

        food.setLocked(false);

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
    }
    public void distributeFoods(ArrayList<Food> foods,int carbCount){
        if(isListenerActive){
            double eachCarbAmount = (double) carbCount/foods.size();
            Toast.makeText(context.getApplicationContext(), String.valueOf(eachCarbAmount),Toast.LENGTH_SHORT).show();
            for(int i=0;i<foods.size();i++){
                foods.get(i).setGram((int)(eachCarbAmount*100/foods.get(i).getCarbAmount()));
                System.out.println((int)(eachCarbAmount*100/foods.get(i).getCarbAmount()));
            }
        }


    }

    public void distributeFoods(int carbCount){
        distributeFoods(foods,carbCount);
    }
    public void makeZero(){
        for(int i=0;i<foods.size();i++){
            foods.get(i).setGram(0);
        }
    }
    public void makeMinusOne(){
        for(int i=0;i<foods.size();i++){
            foods.get(i).setGram(-1);
        }
    }*/
    public double getTotalCarbohydrates() {
        double to = 0;
        for (Food food : foods) {
            if (food.getCarbAmountRespectToGram() >= 0) {
                to += food.getCarbAmountRespectToGram();
            }
        }
        return to;

    }
}
