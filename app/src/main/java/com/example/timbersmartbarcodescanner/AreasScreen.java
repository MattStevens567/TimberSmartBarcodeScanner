package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

//yo
public class AreasScreen extends AppCompatActivity implements Serializable {
    private static final String TAG = "AreasScreen";

    private AreaListAdapter mAreaListAdapter;
    private int mPassedStockTakeIndex;
    private ListView mListView;
    private Button mAddNewArea;
    private EditText mNewAreaName;
    private TextView mRowLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas_screen);

        // This screen constructs when we have clicked on a stocktake, so we need to retrieve
        // that specific stock take it is passed an integer of the index.
        // Called when returning from barcode screen
        Intent intent = getIntent();
        mPassedStockTakeIndex = intent.getIntExtra("Stocktake", -1);

        mListView = findViewById(R.id.rowListView);

        try {
            mAreaListAdapter = new AreaListAdapter(this, R.layout.listview_areas_screen, getStocktakeFromData(mPassedStockTakeIndex).getAreaList());
            mListView.setAdapter(mAreaListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mRowLocation = findViewById(R.id.rowLocation);
        try {
            mRowLocation.setText(getStocktakeFromData(mPassedStockTakeIndex).getStocktakeString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mNewAreaName = findViewById(R.id.rowsAddAreaEdit);

        // On-Click listener for "ADD NEW AREA" button at bottom of screen
        mAddNewArea = findViewById(R.id.rowAddButton);
        mAddNewArea.setOnClickListener(view -> {
            try {
                boolean unique = true;
                String areaName;
                areaName = mNewAreaName.getText().toString();

                if(!mNewAreaName.getText().toString().equals("")){
                    for (int i = 0; i < getStocktakeFromData(mPassedStockTakeIndex).getAreaList().size(); i++) {
                        if (getStocktakeFromData(mPassedStockTakeIndex).getAreaList().get(i).getAreaString().equals(areaName)){
                            unique = false;
                        }
                    }

                    if (unique) {
                        Area mArea = new Area(areaName);
                        getStocktakeFromData(mPassedStockTakeIndex).addArea(mArea);
                    }
                    else {
                        Toast.makeText(AreasScreen.this, areaName + "already exists, please use different name", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AreasScreen.this, "Field is empty, please add in a name for area", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
            Toast.makeText(AreasScreen.this, "Area added", Toast.LENGTH_LONG).show();
            mNewAreaName.getText().clear();
        });

    }

    // Small function to help prevent long lines of code when accessing Data class
    public Stocktake getStocktakeFromData(int i) throws Exception {
        return Data.getDataInstance().getStocktakeList().get(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            writeFileOnInternalStorage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFileOnInternalStorage() throws Exception {
        File path = getApplicationContext().getExternalFilesDir(null);
        File file = new File(path, "my-file-name.txt");
        FileOutputStream stream = new FileOutputStream(file);
        String stringToWriteInFile = Data.getDataInstance().ToString();
        try {
            stream.write(stringToWriteInFile.getBytes());
        } finally {
            stream.close();
        }
    }

    // On-Click listener assigned to ListViews "Add Barcode" Button
    // When clicked will open scanning screen in context to area clicked
    public void addBarcodesToArea(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();
        int areaIndex=0;
        for (int i = 0; i <getStocktakeFromData(mPassedStockTakeIndex).getAreaList().size(); i++) {
            if (getStocktakeFromData(mPassedStockTakeIndex).getAreaList().get(i).getAreaString().equals(item)) {
                areaIndex = i;
            }
        }
        Intent intent = new Intent(AreasScreen.this, ScanningScreen.class);
        intent.putExtra("Stocktake Index", mPassedStockTakeIndex);
        intent.putExtra("Area Index", areaIndex);
        startActivity(intent);
    }

    // On-Click listener assigned to ListViews "Delete Area" Button
    // Deletes selected area from areaList when clicked
    public void deleteArea(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();

        Toast.makeText(this, item +" deleted", Toast.LENGTH_LONG).show();
        for (int i = 0; i <getStocktakeFromData(mPassedStockTakeIndex).getAreaList().size(); i++){
            if (getStocktakeFromData(mPassedStockTakeIndex).getAreaList().get(i).getAreaString().equals(item)){
                getStocktakeFromData(mPassedStockTakeIndex).getAreaList().remove(i);
                update();

                return;
            }

        }
    }


    public void update(){
        mAreaListAdapter.notifyDataSetChanged();
        mListView.invalidateViews();
    }
}