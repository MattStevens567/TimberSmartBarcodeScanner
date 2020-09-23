package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.net.StandardProtocolFamily;

//yo
public class AreasScreen extends AppCompatActivity implements Serializable {
    private static final String TAG = "AreasScreen";
    AreaListAdapter areaListAdapter;
    private int passedStockTakeIndex;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rows_screen);

        mListView = findViewById(R.id.rowListView);
        // This screen constructs when we have clicked on a stocktake, so we need to retrive
        // that specific stock take it is passed an integer of the index.

        // Called when returning from barcode screen
        Intent intent = getIntent();
        passedStockTakeIndex = intent.getIntExtra("Stocktake", -1);
        try {
            areaListAdapter = new AreaListAdapter(this, R.layout.row_area, getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas());
            mListView.setAdapter(areaListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView test = findViewById(R.id.rowLocation);
        try {
            test.setText(getStocktakeFromData(passedStockTakeIndex).getmStringStockTakeName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add Area Button
        Button add = findViewById(R.id.rowAddButton);
        add.setOnClickListener(view -> {
            EditText location = findViewById(R.id.rowsAddAreaEdit);
            try {
                boolean unique = true;
                if(!location.getText().toString().equals("")){
                    for (int i = 0; i<getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().size(); i++) {

                        if (getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().get(i).getmAreaName().equals(location.getText().toString())){
                            unique = false;
                        }
                    }
                    if (unique) {
                        Area mArea = new Area(location.getText().toString());
                        getStocktakeFromData(passedStockTakeIndex).addmStockTakeAreas(mArea);
                    }
                    else {
                        Toast.makeText(AreasScreen.this, "There is already an area which exists under this name, consider renaming the new area", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AreasScreen.this, "Field is empty", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
            Toast.makeText(AreasScreen.this, "Area added", Toast.LENGTH_LONG).show();
            location.getText().clear();
        });

    }

    public Stocktake getStocktakeFromData(int i) throws Exception {
        return Data.getDataInstance().getmStocktakeList().get(i);
    }

    public void AddHandler(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();
        int areaIndex=0;
        Toast.makeText(this, "Going to " + item, Toast.LENGTH_LONG).show();
        for (int i=0;i <getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().size();i++) {
            if (getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().get(i).getmAreaName().equals(item)) {
                areaIndex = i;
            }
        }
        Intent intent = new Intent(AreasScreen.this, ScanningScreen.class);
        intent.putExtra("Stocktake Index",passedStockTakeIndex);
        intent.putExtra("Area Index", areaIndex);
        startActivity(intent);
    }

    public void ViewHandler(View view) {
       // startActivity(new Intent(AreasScreen.this, ScanningScreen.class));
    }

    public void DeleteHandler(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();

        Toast.makeText(this, item +" deleted", Toast.LENGTH_LONG).show();
        for (int i=0;i <getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().size();i++){
            if (getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().get(i).getmAreaName().equals(item)){
                getStocktakeFromData(passedStockTakeIndex).getmStockTakeAreas().remove(i);
                final ListView mListView = findViewById(R.id.rowListView);
                update();

                return;
            }

        }
    }
    public void update(){
        areaListAdapter.notifyDataSetChanged();
        mListView.invalidateViews();
    }
}