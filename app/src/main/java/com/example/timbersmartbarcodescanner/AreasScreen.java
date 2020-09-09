package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AreasScreen extends AppCompatActivity {
    private static final String TAG = "RowsScrean";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rows_screen);

        Log.d(TAG, "onCreate: Started");
        ListView mListView = findViewById(R.id.rowListView);
        //Some Test Data for the meantime
        ArrayList<Area> sampleStockTakes = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            sampleStockTakes.add(new Area("Tyron is awesome: " + i));
        }

        AreaListAdapter test = new AreaListAdapter(this, R.layout.row_area, sampleStockTakes);
        mListView.setAdapter(test);

        Button back = findViewById(R.id.rowAddButton);
        back.setOnClickListener(view -> startActivity(new Intent(AreasScreen.this, ScanningScreen.class)));

    }
}