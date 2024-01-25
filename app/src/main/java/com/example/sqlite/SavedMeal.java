package com.example.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.sqlite.Food;

import java.util.ArrayList;

public class SavedMeal implements Parcelable {

    private ArrayList<Food> listOfFood = new ArrayList<Food>();
    private String typeOfMeal;

    public SavedMeal(){
        this.typeOfMeal = "";
    }

    public SavedMeal(ArrayList<Food> listOfFoodIn,String typeOfMealIn){ 
        for (int i = 0; i<listOfFoodIn.size();i++){
            this.listOfFood.add(listOfFoodIn.get(i));
        }
        this.typeOfMeal = typeOfMealIn;
    }

    protected SavedMeal(Parcel in) {
        listOfFood = in.createTypedArrayList(Food.CREATOR);
        typeOfMeal = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(listOfFood);
        dest.writeString(typeOfMeal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SavedMeal> CREATOR = new Creator<SavedMeal>() {
        @Override
        public SavedMeal createFromParcel(Parcel in) {
            return new SavedMeal(in);
        }

        @Override
        public SavedMeal[] newArray(int size) {
            return new SavedMeal[size];
        }
    };

    public void additionOfFood(Food foodIn){
        this.listOfFood.add(foodIn);
    }

    public void setPropertyClass(String foodClass){
        this.typeOfMeal = foodClass;
    }

    public int[] totalCarbAndFiber(){
        int [] totals = {0,0};

        for (int i = 0; i<getListOfFood().size();i++){
            totals[0] += (this.listOfFood.get(i)).getCarbohydrates();
            totals[1] += (this.listOfFood.get(i)).getFibers();

        }

        return totals;
    }

    public ArrayList<Food> getListOfFood(){
        return listOfFood;
    }

    public String getTypeOfMeal(){
        return typeOfMeal;
    }
    public String toString(){
      String tmp="Type of meal is: "+ typeOfMeal+" and contains the following food: ";
      for(int i=0; i<listOfFood.size();i++){
        tmp+=listOfFood.get(i)+",";
    }
    return tmp;
  }
}
