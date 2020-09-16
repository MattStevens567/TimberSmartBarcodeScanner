package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity implements Serializable {

    private static final String TAG = "ActivityMain";
    static ArrayList<Stocktake> sampleStockTakes = new ArrayList<Stocktake>();
    static int test =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        ListView mListView = findViewById(R.id.ActivityMainListViewStocktakes);
        //Some Test Data for the meantime

        if (test == 0) {
            for (int i = 0; i < 5; i++) {
                sampleStockTakes.add(new Stocktake("Stocktake number: " + i));
            }
            test++;
        }
        else {

            Intent intent = getIntent();

            if (intent.getSerializableExtra("stocktake") != null) {
                Stocktake tester = (Stocktake) intent.getSerializableExtra("stocktake");

                for (int t = 0; t < sampleStockTakes.size(); t++) {

                    if (sampleStockTakes.get(t).getmStringStockTakeName().equals(tester.getmStringStockTakeName())) {
                        Toast.makeText(ActivityMain.this, "updated2", Toast.LENGTH_LONG).show();

                        sampleStockTakes.get(t).setmStockTakeAreas(tester.getmStockTakeAreas());

                    }
                }

            }
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

    public void StockTakeViewHHandler(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();

        int index=0;
        Toast.makeText(this, "Going to " + item, Toast.LENGTH_LONG).show();
        for (int i=0;i <sampleStockTakes.size();i++) {
            if (sampleStockTakes.get(i).getmStringStockTakeName().equals(item)) {
                index = i;

                //return;
            }
        }
        Intent intent = new Intent (ActivityMain.this, AreasScreen.class);
        intent.putExtra("Stocktake", sampleStockTakes.get(index));
        startActivity(intent);
    }
}