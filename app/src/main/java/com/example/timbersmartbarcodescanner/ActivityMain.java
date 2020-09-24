package com.example.timbersmartbarcodescanner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/*
* This class is the back-end for the start up screen, AKA the Stock take screen.
*
* This app works in the following ways:
* A singleton class named Data is used to store the inventory management data.
*
* The data structure is as follows:
* Data consists of many stocktakes
* Stocktakes consist of many areas
* Areas consist of many barcodes.
* Stocktakes, areas and barcodes all have extra fields to them too (date, time ....)
*
*
* This is the first iteration v1.x of the Stick Barcode Scanner created by 3 Massey University Students, namely:
* Keeghan Campbell,
* Matt Stevens,
* Tyron Landman.
* Year: 2020.
* */
public class ActivityMain extends AppCompatActivity implements Serializable {

    private Button mExport;

    private static final String TAG = "ActivityMain";
    ArrayList<Stocktake> sampleStockTakes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default --------------------------
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------------------

        //Set up views -------------------------------
        ListView mListView = findViewById(R.id.ActivityMainListViewStocktakes);
        Button addNew = findViewById(R.id.ActivityMainAddNewStocktake);
        EditText newStocktakeItem = findViewById(R.id.ActivityMainEditStocktake);
        //--------------------------------------------------------------

        // Adding test data ----------------------------------------------------
        sampleStockTakes = new ArrayList<Stocktake>();
        for (int i = 0; i < 5; i++) {
            sampleStockTakes.add(new Stocktake("Stocktake number: " + i*292%100));
        }
        Data.getDataInstance(sampleStockTakes);
        //----------------------------------------------------------------------------

        // Layout stuff----------------------------------------
        StockTakeListAdapter stockTakeListAdapter = new StockTakeListAdapter(this, R.layout.activity_main_adapter_list_view_stocktakes, sampleStockTakes);
        mListView.setAdapter(stockTakeListAdapter);
        //----------------------------------------------------------------------------


//        //Listing for an Area----------------------------------------
//        Intent intent = getIntent();
//        if (intent.getSerializableExtra("stocktake") != null) {
//            Stocktake tester = (Stocktake) intent.getSerializableExtra("stocktake");
//            for (int t = 0; t < sampleStockTakes.size(); t++) {
//                if (sampleStockTakes.get(t).getmStringStockTakeName().equals(tester.getmStringStockTakeName())) {
//                    sampleStockTakes.get(t).setmStockTakeAreas(tester.getmStockTakeAreas());
//                }
//            }
//
//        }
//        //----------------------------------------------------------------------------

        // Add addNew stocktake button onclick listener----------------
        addNew.setOnClickListener(view -> {
            String newStocktakeName = newStocktakeItem.getText().toString();

            try {
                boolean unique = true;
                for (int i =0; i<Data.getDataInstance().getmStocktakeList().size();i++){
                    if(newStocktakeName.equals("")){
                        unique =false;
                        break;
                    }
                    if (Data.getDataInstance().getmStocktakeList().get(i).getmStringStockTakeName().equals(newStocktakeName)){
                        unique =false;
                        break;
                    }

                }
                if(unique){
                    Stocktake temp = new Stocktake(newStocktakeName );
                    Data.getDataInstance().addTomStocktakeList(temp);
                    stockTakeListAdapter.notifyDataSetChanged();
                    newStocktakeItem.setText("");
                }
                else if(newStocktakeName.equals("")){
                    Toast.makeText(this, "Field is empty, please add in a name for the Stocktake", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Name in use, Please choose another", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //----------------------------------------------------------------------------

//        Button back = findViewById(R.id.ActivityMainAddNewStocktake);
//        back.setOnClickListener(view -> startActivity(new Intent(ActivityMain.this, AreasScreen.class)));

    }

    public void StockTakeViewHHandler(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String stockTakeClicked = child.getText().toString();

        int index=0;
        int stockTakeListSize = Data.getDataInstance().getmStocktakeList().size();
        ArrayList<Stocktake> tempStocktakes = Data.getDataInstance().getmStocktakeList();
        for (int i=0;i<tempStocktakes.size(); i++){
            if (tempStocktakes.get(i).getmStringStockTakeName().equals(stockTakeClicked )){
                index = i;
                break;
            }
        }

        // Passes an intent which holds the index of a stock take
        Intent intent = new Intent (ActivityMain.this, AreasScreen.class);
        intent.putExtra("Stocktake", index);
        startActivity(intent);
    }

    public void export(View view) throws Exception {

//        LinearLayout parent = (LinearLayout) view.getParent();
//        TextView child = (TextView)parent.getChildAt(0);
//        String stockTakeClicked = child.getText().toString();
//
//        int index = 0;
//        int stockTakeListSize = Data.getDataInstance().getmStocktakeList().size();
//        ArrayList<Stocktake> tempStocktakes = Data.getDataInstance().getmStocktakeList();
//        for (int i = 0; i < stockTakeListSize; i++) {
//            if (tempStocktakes.get(i).getmStringStockTakeName().equals(stockTakeClicked)) {
//                index = i;
//                break;
//            }
//        }
//
//        // Generate Data from stocktake
//        StringBuilder data = new StringBuilder();
//        data.append("Stocktake Name, Stocktake Created Date, StockTake modified date, Area Name, Barcode, Scan Date");
//
//        for(int i = ; i < ) {
//            data.append("\n" + i + ',' + i*i);
//        }
        StringBuilder data = new StringBuilder();
        data.append("Barcode, Another Thing");
        for(int i = 0; i < 19; i++) {
            data.append("\n" + i + ',' + i*i);
        }

        try {
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write(data.toString().getBytes());
            out.close();

            // exporting
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.timbersmartbarcodescanner.fileProvider", fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send Mail"));
            Toast.makeText(this, "test", Toast.LENGTH_LONG ).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}