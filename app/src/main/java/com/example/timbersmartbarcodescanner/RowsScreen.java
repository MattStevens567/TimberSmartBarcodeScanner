package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RowsScreen extends AppCompatActivity {
    private static final String TAG = "RowsScrean";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rows_screen);

        Log.d(TAG, "onCreate: Started");
        ListView mListView = findViewById(R.id.rowListView);
        //Some Test Data for the meantime
        ArrayList<Area> sampleStockTakes = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            sampleStockTakes.add(new Area("Tyron is awesome: " + i));
        }

        AreaListAdapter test = new AreaListAdapter(this, R.layout.row_area, sampleStockTakes);
        mListView.setAdapter(test);

        Button back = findViewById(R.id.rowAddButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RowsScreen.this, ScanningScreen.class));
            }
        });

    }
}