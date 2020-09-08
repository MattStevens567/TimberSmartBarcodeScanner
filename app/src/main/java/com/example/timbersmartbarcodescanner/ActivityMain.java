package com.example.timbersmartbarcodescanner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    ListView ActivityMainListViewStocktake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainListViewStocktake = findViewById(R.id.ActivityMainListViewStocktakes);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Stocktake 1");
        arrayList.add("Stocktake 2");
        arrayList.add("Stocktake 3");


    }
}