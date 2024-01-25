package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<String> data;
    private Fragment activity;
    public CustomAdapter (List<String> data,Fragment activity){
        this.data = data;
        this.activity = activity;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(rowItem,this.activity);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        holder.textView.setText(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textView;
        private Fragment activity;

        public ViewHolder(View view, Fragment activity) {
            super(view);
            view.setOnClickListener(this);
            this.textView = view.findViewById(R.id.meals);
            this.activity = activity;
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.textView.getText(), Toast.LENGTH_SHORT).show();
            Toast.makeText(view.getContext(), "blabla", Toast.LENGTH_SHORT).show();
            System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEE BOIIIIIIIIIIIIIIIIII"+this.textView.getText());

            CalculatedFragment calculatedFragment = new CalculatedFragment();

            Bundle bundle = new Bundle();
            DBManager DB = new DBManager(this.activity.getActivity(),"Food.db");

            SavedMeal currentMeal = new SavedMeal();

            //Cursor spot = DB.getTableInformation(this.textView.getText().toString());
            currentMeal = DB.getTableInformation(this.textView.getText().toString());

            bundle.putParcelable("currentMeal", (Parcelable) currentMeal);
            calculatedFragment.setArguments(bundle);

            ((MainActivity) this.activity.getActivity()).fragmentOpenner(calculatedFragment);
        }
    }
}