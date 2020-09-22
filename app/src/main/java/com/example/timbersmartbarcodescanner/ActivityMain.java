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
    ArrayList<Stocktake> sampleStockTakes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        ListView mListView = findViewById(R.id.ActivityMainListViewStocktakes);
        //Some Test Data for the meantime

        // Adding test data ----------------------------------------------------
        sampleStockTakes = new ArrayList<Stocktake>();
        for (int i = 0; i < 5; i++) {
            sampleStockTakes.add(new Stocktake("Stocktake number: " + i*292%100));
        }
        Data.getDataInstance(sampleStockTakes);
        //----------------------------------------------------------------------------



        //Listing for an Area----------------------------------------
        Intent intent = getIntent();
        if (intent.getSerializableExtra("stocktake") != null) {
            Stocktake tester = (Stocktake) intent.getSerializableExtra("stocktake");
            for (int t = 0; t < sampleStockTakes.size(); t++) {
                if (sampleStockTakes.get(t).getmStringStockTakeName().equals(tester.getmStringStockTakeName())) {
                    sampleStockTakes.get(t).setmStockTakeAreas(tester.getmStockTakeAreas());
                }
            }

        }
        //----------------------------------------------------------------------------



        // Layout stuff----------------------------------------
        StockTakeListAdapter stockTakeListAdapter = new StockTakeListAdapter(this, R.layout.activity_main_adapter_list_view_stocktakes, sampleStockTakes);
        mListView.setAdapter(stockTakeListAdapter);

        Button back = findViewById(R.id.ActivityMainAddNewStocktake);
        back.setOnClickListener(view -> startActivity(new Intent(ActivityMain.this, AreasScreen.class)));
        //----------------------------------------------------------------------------
    }
    public void StockTakeViewHHandler(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String stockTakeClicked = child.getText().toString();

        int index=0;
        int stockTakeListSize = Data.getDataInstance().getmStocktakeList().size();
        ArrayList<Stocktake> tempStocktakes = Data.getDataInstance().getmStocktakeList();
        for (int i=0;i < stockTakeListSize; i++) {
            if (tempStocktakes.get(i).getmStringStockTakeName().equals(stockTakeClicked)) {
                index = i;
                break;
            }
        }
        // Passes an intent which holds the index of a stock take
        Intent intent = new Intent (ActivityMain.this, AreasScreen.class);
        intent.putExtra("Stocktake", index);
        startActivity(intent);
    }
}