package com.example.sqlite;

import android.app.AlertDialog;
import android.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.content.DialogInterface;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

public class CalculatedFragment extends Fragment {
    SavedMeal currentMeal;
    private Button AddElementToSavedMeal;
    private TextView CarbsAmountOutput, FibersAmountOutput, StarterOutput,  MainMealOutput, DrinksOutput, CondimentsOutput;
    private Button buttonBack;

    //PieChart variables
    private PieChart pieChart1;
    private PieChart pieChart2;
    private PieChart pieChart3;
    private PieChart pieChart4;
    //private PieChart[] pieCharts = {pieChart1,pieChart2,pieChart3,pieChart4};
    private SavedMeal PartitionnedMeal1 = new SavedMeal();
    private SavedMeal PartitionnedMeal2 = new SavedMeal();
    private SavedMeal PartitionnedMeal3 = new SavedMeal();
    private SavedMeal PartitionnedMeal4 = new SavedMeal();
    private SavedMeal[] PartitionnedSavedMeal = {PartitionnedMeal1,PartitionnedMeal2,PartitionnedMeal3,PartitionnedMeal4};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calculated, container, false);

        CarbsAmountOutput = (TextView) rootView.findViewById(R.id.CarbsAmountOutput);


        FibersAmountOutput = (TextView) rootView.findViewById(R.id.FibersAmountOutput);

        buttonBack = rootView.findViewById(R.id.ButtonBack);
        buttonBack.setOnClickListener(buttonBackListener);

        AddElementToSavedMeal = rootView.findViewById(R.id.AddElementToSavedMeal);
        AddElementToSavedMeal.setOnClickListener(addElementSavedMeal);
        Bundle bundle = getArguments();
        currentMeal = (SavedMeal) bundle.getParcelable("currentMeal");
        StarterOutput = (TextView) rootView.findViewById(R.id.StarterOutput);
        MainMealOutput = (TextView) rootView.findViewById(R.id.MainMealOutput);
        DrinksOutput = (TextView) rootView.findViewById(R.id.DrinksOutput);
        CondimentsOutput = (TextView) rootView.findViewById(R.id.CondimentsOutput);



        PartitionnedSavedMeal = mealDisplay(currentMeal,PartitionnedSavedMeal);
        mealTotal(currentMeal);

        //Piechart Setups
        pieChart1 = rootView.findViewById(R.id.StarterPiechart);
        pieChart2 = rootView.findViewById(R.id.MainMealPiChart);
        pieChart3 = rootView.findViewById(R.id.DrinksPieChart);
        pieChart4 = rootView.findViewById(R.id.CondimentPieChart);

        setupPieCharts(pieChart1);
        setupPieCharts(pieChart2);
        setupPieCharts(pieChart3);
        setupPieCharts(pieChart4);
        loadPieChartData(PartitionnedSavedMeal[0],pieChart1);
        loadPieChartData(PartitionnedSavedMeal[1],pieChart2);
        loadPieChartData(PartitionnedSavedMeal[2],pieChart3);
        loadPieChartData(PartitionnedSavedMeal[3],pieChart4);

        return rootView;
    }

    private SavedMeal[] mealDisplay(SavedMeal currentMeal, SavedMeal[] PartitionnedSavedMeal) {
        int i;

        for (i = 0;i<currentMeal.getListOfFood().size(); i++){
            Food food = currentMeal.getListOfFood().get(i);
            String subesection = food.getSubsectionOfFood();
            switch (subesection) {
                case "Starter" :
                    //StarterOutput.setText(food.BasictoString());
                    PartitionnedSavedMeal[0].additionOfFood(currentMeal.getListOfFood().get(i));
                    break;
                case "Main Meal" :
                    //MainMealOutput.setText(food.BasictoString());
                    PartitionnedSavedMeal[1].additionOfFood(currentMeal.getListOfFood().get(i));
                    break;
                case "Drinks" :
                    //DrinksOutput.setText(food.BasictoString());
                    PartitionnedSavedMeal[2].additionOfFood(currentMeal.getListOfFood().get(i));
                    break;
                case "Condiments" :
                    //CondimentsOutput.setText(food.BasictoString());
                    PartitionnedSavedMeal[3].additionOfFood(currentMeal.getListOfFood().get(i));
                    break;
            }
        }
        return PartitionnedSavedMeal;
    }

    private void mealTotal(SavedMeal currentMeal) {
        int i;
        int totalAmountOfCarbs = 0;
        int totalAmountOfFibers= 0;
        for (i = 0; i < currentMeal.getListOfFood().size(); i++) {
            Food food = currentMeal.getListOfFood().get(i);
            totalAmountOfCarbs = totalAmountOfCarbs + food.getCarbohydrates();
            totalAmountOfFibers = totalAmountOfFibers + food.getFibers();
        }
        CarbsAmountOutput.setText(String.valueOf(totalAmountOfCarbs));
        FibersAmountOutput.setText(String.valueOf(totalAmountOfFibers));
    }


    private void setupPieCharts(PieChart currentPieChart) {
        for (int i = 0;i<1;i++){
            currentPieChart.setDrawHoleEnabled(true); // donut pie
            currentPieChart.setUsePercentValues(true);
            currentPieChart.setEntryLabelTextSize(12);
            currentPieChart.setEntryLabelColor(Color.BLACK);
            currentPieChart.setCenterText("");
            currentPieChart.setCenterTextSize(24);
            currentPieChart.getDescription().setEnabled(false);

            Legend legend = currentPieChart.getLegend();
            legend.setEnabled(false);

        }
    }

    private void loadPieChartData(SavedMeal PartitionnedSavedMeal,PieChart currentPieChart) {



        ArrayList<PieEntry> entries = new ArrayList<>();

        // test values, we'd have to use the elements of currentMeal
        // exemple currentMeal.listoffood().getName()
        for (int i = 0;i<PartitionnedSavedMeal.getListOfFood().size();i++){
            entries.add(new PieEntry(PartitionnedSavedMeal.getListOfFood().get(i).getCarbohydrates(), PartitionnedSavedMeal.getListOfFood().get(i).getName()));
        }//PartitionnedSavedMeal.getListOfFood().get(i).getName()

        /*entries.add(new PieEntry(0.2f, "food"));
        entries.add(new PieEntry(0.5f, "food2"));*/
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for(int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Foods");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(currentPieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        currentPieChart.setData(data);
        currentPieChart.invalidate();

        // can do some animation, haven't look at it too much tbh
        currentPieChart.animateY(1000, Easing.EaseInOutQuad);


    }


    private View.OnClickListener addElementSavedMeal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CustomPopup customPopup = new CustomPopup(getActivity());
            String str = customPopup.radioButtonCheck();

            customPopup.getButtonBack().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customPopup.dismiss();
                }
            });

            customPopup.getButtonProceed().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customPopup.getMealName().isEmpty() ) {
                        Toast.makeText(getActivity(), "Enter the meal name plz", Toast.LENGTH_SHORT).show();
                    } else {
                        DBManager DB = new DBManager(getActivity(), "Food.db");
                        DB.createTable(customPopup.getMealName());
                        String category = customPopup.radioButtonCheck();
                        Toast.makeText(getActivity(), "type of MEAL: "+ category, Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < currentMeal.getListOfFood().size(); i++) {
                            Food food = currentMeal.getListOfFood().get(i);

                            DB.addOne(food.getName(), food.getCarbohydrates(), food.getFibers(), food.getSubsectionOfFood(), category);
                        }

                        customPopup.dismiss();
                    }

                }
            });

            customPopup.build();

        }

    };

    private View.OnClickListener buttonBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            CalculationFragment calculationFragment = new CalculationFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable("currentMeal", (Parcelable) currentMeal);
            calculationFragment.setArguments(bundle);

            ((MainActivity) getActivity()).fragmentOpenner(calculationFragment);

        }
    };

}
