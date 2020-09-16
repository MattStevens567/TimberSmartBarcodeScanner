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

import java.io.Serializable;
import java.net.StandardProtocolFamily;

//yo
public class AreasScreen extends AppCompatActivity implements Serializable {
   // ArrayList<Area> sampleStockTakes = new ArrayList<>();
    private static final String TAG = "RowsScrean";
    static private Stocktake stocktake = new Stocktake("test");
   // Stocktake stocktake = (Stocktake)intent.getExtras().getString("Stocktake") ;
    //Stocktake stocktake = getIntent().("Stocktake");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rows_screen);

       // if(test == 0){
            Intent intent = getIntent();
            stocktake.setmStockTakeAreas(((Stocktake)intent.getSerializableExtra("Stocktake")).getmStockTakeAreas());
            stocktake.setmStringStockTakeName(((Stocktake)intent.getSerializableExtra("Stocktake")).getmStringStockTakeName());

            update();
        //}

        Log.d(TAG, "onCreate: Started");
        //ListView mListView = findViewById(R.id.rowListView);
        //Some Test Data for the meantime

//        for (int i = 0; i < 8; i++){
//            //sampleStockTakes.add(new Area("Tyron is awesome: " + i));
//            stocktake.setmStockTakeAreas(new Area("Tyron is awesome: " + i));
//        }
        TextView test = findViewById(R.id.rowLocation);
        test.setText(stocktake.getmStringStockTakeName());

        //update();

        Button back = findViewById(R.id.back);
        back.setOnClickListener(view ->{
            Intent intents = new Intent(AreasScreen.this, ActivityMain.class);
            intents.putExtra("stocktake",stocktake);
            startActivity(intents);});

        Button add = findViewById(R.id.rowAddButton);
        add.setOnClickListener((View.OnClickListener) view -> {
            EditText location =  (EditText) findViewById(R.id.rowsAddAreaEdit);
            Area mArea = new Area(location.getText().toString());
            stocktake.addmStockTakeAreas(mArea);
            update();
            Toast.makeText(AreasScreen.this, "Area added", Toast.LENGTH_LONG).show();
            location.getText().clear();
        });

    }

    public void AddHandler(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();
        int index=0;
        Toast.makeText(this, "Going to " + item, Toast.LENGTH_LONG).show();
        for (int i=0;i <stocktake.getmStockTakeAreas().size();i++) {
            if (stocktake.getmStockTakeAreas().get(i).getmAreaName().equals(item)) {
                index = i;
            }
        }
        Intent intent = new Intent(AreasScreen.this, ScanningScreen.class);
        intent.putExtra("Area",stocktake.getmStockTakeAreas().get(index));
        startActivity(intent);
    }

    public void ViewHandler(View view) {
       // startActivity(new Intent(AreasScreen.this, ScanningScreen.class));
    }

    public void DeleteHandler(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();

        Toast.makeText(this, item +" deleted", Toast.LENGTH_LONG).show();
        for (int i=0;i <stocktake.getmStockTakeAreas().size();i++){
            if (stocktake.getmStockTakeAreas().get(i).getmAreaName().equals(item)){
                stocktake.getmStockTakeAreas().remove(i);
                final ListView mListView = findViewById(R.id.rowListView);
                update();

                return;
            }

        }
    }
    public void update(){
        ListView mListView = findViewById(R.id.rowListView);
        AreaListAdapter update = new AreaListAdapter(this, R.layout.row_area, stocktake.getmStockTakeAreas());
        mListView.setAdapter(update);
    }
}