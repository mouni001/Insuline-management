 /**
 * This class helps store and manipulate information about food
 * 
 */
package com.example.sqlite;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {
    private int carbohydrates = 0;
    private int fibers = 0;
    private String name = "";
    private String subsectionOfFood = "";
    private String picture = "";

    /**
     * 
     * @param carb stores the amount of carbohydrates of a food
     * @param fib stores the amount of fibers of a a food
     * 
     * @param nam stores the name of the food
     * @param subsection stores the kind of food
     * @param pic stores a picture of the food
     */
    public Food(int carb, int fib, String nam, String subsection, String pic){
        this.carbohydrates = carb;
        this.fibers = fib;
        
        this.name = nam;
        this.subsectionOfFood = subsection;
        this.picture = pic;
    }

    public Food(int carb, String nam, String subsection, String pic){
        this.carbohydrates = carb;
        this.fibers = 0;
        
        this.name = nam;
        this.subsectionOfFood = subsection;
        this.picture = pic;
    }

    public Food(int carb, int fib, String nam, String subsection){
        this.carbohydrates = carb;
        this.fibers = fib;
        
        this.name = nam;
        this.subsectionOfFood = subsection;
        this.picture = null;
    }
    
    public Food(int carb, String nam, String subsection){
        this.carbohydrates = carb;
        this.fibers = 0;
        
        this.name = nam;
        this.subsectionOfFood = subsection;
        this.picture = null;
    }


    protected Food(Parcel in) {
        carbohydrates = in.readInt();
        fibers = in.readInt();
        name = in.readString();
        subsectionOfFood = in.readString();
        picture = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public int getCarbohydrates(){
        return this.carbohydrates;
    }

    public int getFibers(){
        return this.fibers;
    }

    public String getName(){
        return this.name;
    }

    public String getSubsectionOfFood(){
        return this.subsectionOfFood;
    }

    public String getPicture(){
        return picture;
    }

    public void setCarbohydrates(int carb){
        this.carbohydrates = carb;
    }

    public void setFibers(int fib){
        this.fibers = fib;
    }

    public void setName(String nam){
        this.name = nam;
    }

    public void setSubsectionOfFood(String subSection){
        this.subsectionOfFood = subSection;
    }

    public void setPicture(String pic){
        this.picture = pic;
    }

    public void incrementCarbohydrates(){
        this.carbohydrates++;
    }

    public void decrementCarbohydrates(){
        this.carbohydrates--;
    }

    public void incrementFibers(){
        this.fibers++;
    }

    public void decrementFibers(){
        this.fibers--;
    }

    public String toString(){
        return "Here is information about " + this.name + ", part of "+this.subsectionOfFood+" which contains the following:\ncarbs: " + this.carbohydrates+"\nfibers: "+this.fibers+"\nThe picture associated to this Food is stored as "+this.picture+".";
    }
    public String BasictoString(){
        return "carbs: "+ this.carbohydrates+"\nfibers: "+this.fibers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(carbohydrates);
        dest.writeInt(fibers);
        dest.writeString(name);
        dest.writeString(subsectionOfFood);
        dest.writeString(picture);
    }
}
