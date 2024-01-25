package com.example.sqlite;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CustomPopup extends Dialog {
    private EditText mealName;
    private RadioButton radioButtonBreakfast, radioButtonLunch, radioButtonDinner, radioButtonSnacks;
    private Button buttonBack, buttonProceed;

    public CustomPopup(Activity activity){
        super(activity, R.style.Theme_MaterialComponents_DayNight_Dialog);
        setContentView(R.layout.popup);
        this.mealName =(EditText) findViewById(R.id.MealName);;
        this.radioButtonBreakfast = (RadioButton) findViewById(R.id.radioButtonBreakfast);
        this.radioButtonLunch = (RadioButton) findViewById(R.id.radioButtonLunch);
        this.radioButtonDinner = (RadioButton) findViewById(R.id.radioButtonDinner);
        this.radioButtonSnacks = (RadioButton) findViewById(R.id.radioButtonSnacks);

        this.buttonBack = findViewById(R.id.ButtonBack);
        this.buttonProceed = findViewById(R.id.ButtonProceed);
        this.radioButtonBreakfast.setChecked(true);


    }

    public Button getButtonBack(){
        return this.buttonBack;
    }

    public Button getButtonProceed(){
        return this.buttonProceed;
    }

    public String getMealName() {
        return this.mealName.getText().toString();
    }

    public String radioButtonCheck(){
        if (this.radioButtonBreakfast.isChecked()){


            return "Breakfast";
        } else if (this.radioButtonLunch.isChecked()){

            return "Lunch";
        } else if (this.radioButtonDinner.isChecked()){

            return "Dinner";
        } else if (this.radioButtonSnacks.isChecked()){

            return "Snacks";
        } else {
            return "error";
        }
    }


    public void build(){
        show();

    }
}
