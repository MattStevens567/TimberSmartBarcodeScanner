package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    private static final String TAG = "ActivityMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        ListView mListView = findViewById(R.id.ActivityMainListViewStocktakes);
        //Some Test Data for the meantime
        ArrayList<Stocktake> sampleStockTakes = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            sampleStockTakes.add(new Stocktake("Stocktake number: " + i));
        }

        StockTakeListAdapter stockTakeListAdapter = new StockTakeListAdapter(this, R.layout.activity_main_adapter_list_view_stocktakes, sampleStockTakes);
        mListView.setAdapter(stockTakeListAdapter);

        Button back = findViewById(R.id.ActivityMainAddNewStocktake);
        back.setOnClickListener(view -> startActivity(new Intent(ActivityMain.this, AreasScreen.class)));
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanning_screen);
//        Log.d(TAG, "onCreate: Started");
//
//    }


}