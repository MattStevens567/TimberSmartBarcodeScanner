package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AreasScreen extends AppCompatActivity {
    ArrayList<Area> sampleStockTakes = new ArrayList<>();
    private static final String TAG = "RowsScrean";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rows_screen);

        Log.d(TAG, "onCreate: Started");
        //ListView mListView = findViewById(R.id.rowListView);
        //Some Test Data for the meantime

        for (int i = 0; i < 8; i++){
            sampleStockTakes.add(new Area("Tyron is awesome: " + i));
        }

        update();

//        Button back = findViewById(R.id.rowAddButton);
//        back.setOnClickListener(view -> startActivity(new Intent(AreasScreen.this, ScanningScreen.class)));
        Button add = findViewById(R.id.rowAddButton);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText location =  (EditText) findViewById(R.id.rowsAddAreaEdit);
                sampleStockTakes.add(new Area(location.getText().toString()));

                update();
                Toast.makeText(AreasScreen.this, "Area added", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void AddHandler(View view) {
        //LinearLayout parent = (LinearLayout) view.getParent();
        startActivity(new Intent(AreasScreen.this, ScanningScreen.class));
    }

    public void ViewHandler(View view) {
       // startActivity(new Intent(AreasScreen.this, ScanningScreen.class));
    }

    public void DeleteHandler(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();

        Toast.makeText(this, item +" deleted", Toast.LENGTH_LONG).show();
        for (int i=0;i <sampleStockTakes.size();i++){
            if (sampleStockTakes.get(i).getmAreaName().equals(item)){
                sampleStockTakes.remove(i);
                final ListView mListView = findViewById(R.id.rowListView);
                update();

                return;
            }

        }
    }
    public void update(){
        ListView mListView = findViewById(R.id.rowListView);
        AreaListAdapter update = new AreaListAdapter(this, R.layout.row_area, sampleStockTakes);
        mListView.setAdapter(update);
    }
}