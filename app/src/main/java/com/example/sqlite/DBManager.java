package com.example.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import static android.content.ContentValues.TAG;


public class DBManager extends SQLiteOpenHelper {
    // Since we have 2 databases, we'll have to know which to call/create with variable
    public static String DatabaseName;
    public static String TableName;

    SQLiteDatabase db = getWritableDatabase();

    // databaseName must be "name.db"
    public DBManager(@Nullable Context context, String DatabaseName, String TableName) {
        super(context, DatabaseName, null, 1);

        // We need this to know which table we'll create on the onCreate method.
        this.DatabaseName = DatabaseName;
        this.TableName = TableName;
    }

    public DBManager(@Nullable Context context, String DatabaseName) {
        super(context, DatabaseName, null, 1);

        // We need this to know which table we'll create on the onCreate method.
        this.DatabaseName = DatabaseName;
    }

    // Called the first time the database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableStatement;
        //createTableStatement = "CREATE TABLE " + this.TableName + "(FoodVariable TEXT, Carbs INTEGER, Fibers INTEGER, SubSection TEXT, Category TEXT)";
        //db.execSQL(createTableStatement);
    }

    // Modify existing database with new elements (simplification)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + this.TableName);
        //onCreate(db);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    public void createTable(String TableName){
        this.TableName = TableName;
        String createTableStatement;
        createTableStatement = "CREATE TABLE " + TableName + "(FoodVariable TEXT, Carbs INTEGER, Fibers INTEGER, SubSection TEXT, Category TEXT)";
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        db.execSQL(createTableStatement);

    }

    public void setTableName(String TableName) {
        this.TableName = TableName;
    }

    public boolean addOne(String foodVariable, int carbs, int fibers, String subsection, String category) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put("FoodVariable", foodVariable);
        cv.put("Carbs", carbs);
        cv.put("Fibers", fibers);
        cv.put("SubSection", subsection);
        cv.put("Category", category);

        long insert = db.insert(this.TableName, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean deleteOne(String nameOfFood) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + this.TableName + " WHERE FoodName = " + nameOfFood;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }



    public ArrayList<String> viewData() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> arrTblNames = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }


        }
        return arrTblNames;
    }

    public String getCategory(String TableNam){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Category FROM " + TableNam, null);

        if (cursor.moveToFirst()) {
            String res = cursor.getString(0);
            return res;
        } else {
            return "non";
        }

    }

    public SavedMeal getTableInformation(String TableNam){
        SavedMeal currentMeal = new SavedMeal();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TableNam, null);

        while (cursor.moveToNext()){
            currentMeal.additionOfFood(new Food(cursor.getInt(1),cursor.getInt(2),cursor.getString(0),cursor.getString(3)));
        }
        return currentMeal;

    }
}