package com.example.sqlite;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CalculationFragment extends Fragment {
    public SavedMeal currentMeal = new SavedMeal();
    private int numbersOfElements;
    private String subsectionName;

    private PieChart pieChart;
    private TextView Subsection;
    private EditText ElementName;
    private EditText Carb;
    private EditText Fiber;
    private  Button Starter, MainMeal, Drinks, Condiments, Calculate, AddElement;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calculate, container, false);

        Subsection = (TextView) rootView.findViewById(R.id.SubsectionName);
        // By default we are on the starter subsection
        subsectionName = "Main Meal";
        Subsection.setText(subsectionName);

        // Piechart element
        pieChart = rootView.findViewById(R.id.CalculationPiechart);

        // Editable text
        ElementName = (EditText)rootView.findViewById(R.id.ElementName);
        Carb = (EditText)rootView.findViewById(R.id.Carb);
        Fiber = (EditText)rootView.findViewById(R.id.Fiber);

        // Buttons
        Starter = rootView.findViewById(R.id.Starter);
        MainMeal = rootView.findViewById(R.id.MainMeal);
        Drinks = rootView.findViewById(R.id.Drinks);
        Condiments = rootView.findViewById(R.id.Condiments);
        AddElement = rootView.findViewById(R.id.AddElement);

        Calculate = rootView.findViewById(R.id.Calculate);

        Calculate.setOnClickListener(calculateListener);

        Starter.setOnClickListener(starterListener);
        MainMeal.setOnClickListener(mainMealListener);
        Drinks.setOnClickListener(drinkListener);
        Condiments.setOnClickListener(condimentsListener);

        if (getArguments() != null){
            Bundle bundle = getArguments();
            currentMeal = (SavedMeal) bundle.getParcelable("currentMeal");
        }

        AddElement.setOnClickListener(addElement);

        setupPieChart();

        loadPieChartData(currentMeal);
        return rootView ;
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true); // donut pie
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);


        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        // enable it if you wanna see how it looks
        legend.setEnabled(false);
    }
    private void loadPieChartData(SavedMeal currentMeal) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        // test values, we'd have to use the elements of currentMeal
        // exemple currentMeal.listoffood().getName()

        for (int i = 0;i<currentMeal.getListOfFood().size();i++){
            entries.add(new PieEntry(currentMeal.getListOfFood().get(i).getCarbohydrates(), currentMeal.getListOfFood().get(i).getName()));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for(int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "food lol");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        // can do some animation, haven't look at it too much tbh
        pieChart.animateY(1000, Easing.EaseInOutQuad);


    }
    private View.OnClickListener calculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (currentMeal.getListOfFood().size()>0) {
                CalculatedFragment calculatedPage = new CalculatedFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("currentMeal", (Parcelable) currentMeal);
                calculatedPage.setArguments(bundle);

                ((MainActivity) getActivity()).fragmentOpenner(calculatedPage);
            } else {
                Toast.makeText(getActivity(),"you dont", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener addElement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ElementName.getText().toString().isEmpty() || Carb.getText().toString().isEmpty() || Fiber.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "enter your data fully", Toast.LENGTH_SHORT).show();

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalculationFragment()).commit();

            } else {

                /*
                * Carb.getText().clear() and
                * Carb.setText("")
                * dont work
                * idk why
                * plz help
                *
                * */
                Food starter = new Food(Integer.parseInt(Carb.getText().toString()), Integer.parseInt(Fiber.getText().toString()), ElementName.getText().toString(), subsectionName );
                currentMeal.additionOfFood(starter);

                loadPieChartData(currentMeal);

                ElementName.setText("");


                Toast.makeText(getActivity(),"Food have been added" + currentMeal.getListOfFood().size(), Toast.LENGTH_LONG).show();
            }

        }
    };



    private View.OnClickListener starterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            subsectionName = "Starter";
            Subsection.setText(subsectionName);

        }
    };

    private View.OnClickListener mainMealListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            subsectionName = "Main Meal";
            Subsection.setText(subsectionName);

        }
    };

    private View.OnClickListener drinkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            subsectionName = "Drinks";
            Subsection.setText(subsectionName);

        }
    };

    private View.OnClickListener condimentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            subsectionName = "Condiments";
            Subsection.setText(subsectionName);

        }
    };


}
