package com.example.timbersmartbarcodescanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ScanningScreen extends Activity implements Serializable {

    private static final String TAG = "ScanningScreen";

    private int mCountGlobal, mPreCountGlobal;

    private TextView mCount, mDifference;
    private EditText mBarcode, mPreCount;
    private Button mEnter, mConfirmPreCount;
    private ListView mListView;


    private BarcodeListAdapter mBarcodeListAdapter;


    int passedAreaIndex, passedStocktakeIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_screen);

        //grab Area object from AreaScreen
        Intent intent = getIntent();
        passedAreaIndex = intent.getIntExtra("Area Index", -1);
        passedStocktakeIndex = intent.getIntExtra("Stocktake Index", -1);




        Log.d(TAG, "onCreate: Started");

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void init() throws Exception {

        // Assigning views to variables
        mCount = findViewById(R.id.textViewCount);
        mDifference = findViewById(R.id.textViewDifference);
        mBarcode = findViewById(R.id.editTextBarcode);
        mPreCount = findViewById(R.id.editTextPreCount);
        mEnter = findViewById(R.id.buttonEnter);
        mConfirmPreCount = findViewById(R.id.buttonConfirmPreCount);
        mListView = findViewById(R.id.ScanningScreenListView);


        // Set this to be the value passed from area
        // or just count how many barcodes are currently in array
        mCountGlobal = 0;
        mPreCountGlobal = 0;

        mCount.setText(String.valueOf(mCountGlobal));
        mPreCount.setHint("Enter PreCount");
        mDifference.setText("0");
        mPreCount.setText("0");



        mBarcodeListAdapter = new BarcodeListAdapter(this, R.layout.scanning_screen_listview_layout, getAreaOnFromPassedInstance().getBarcodeList());
        mListView.setAdapter(mBarcodeListAdapter);

        //update();

        // When enter is pressed, adds on a \n character
        // Program then picks up this change and saves the barcode
        mEnter.setOnClickListener((View v) -> {
            String temp = "";
            try {
                temp = mBarcode.getText().toString();
                temp = temp + "\n";
            }
            catch(Exception ex) {
                Context context = getApplicationContext();
                int duration  = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }
            mBarcode.setText(temp);
        });

        mConfirmPreCount.setOnClickListener((View v) -> {
            String tempString = mPreCount.getText().toString();
            int tempPreCount;

            if(tempString.equals("")) {
                tempPreCount = 0;
            } else {
                tempPreCount = Integer.parseInt(tempString);
            }

            mPreCountGlobal = tempPreCount;
            calculateDifference();
            mBarcode.requestFocus();

        });


        initTextWatchers();

    }

    public Area getAreaOnFromPassedInstance() throws Exception {
        return Data.getDataInstance().getStocktakeList().get(passedStocktakeIndex).getmStockTakeAreas().get(passedAreaIndex);
    }

    public void initTextWatchers() {
        // barcodeTextWatcher
        TextWatcher barcodeTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Unused
            }

            @Override
            public void afterTextChanged(Editable barcodeEditable) {
                String temp = barcodeEditable.toString();
                if(temp.contains("\n")) {

                    // Only shorten barcode if it had more then just \n
                    if(temp.length() > 0) {
                        temp = temp.substring(0, temp.length() - 1);

                    }

                    CharSequence text = "Barcode " + temp + " found";
                    Log.d(TAG, text.toString());
                    Context context = getApplicationContext();
                    int duration  = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    // Send Barcode string to addBarcodeLogic function
                    // This function handles DateTime etc. to create barcode object
                    try {
                        addBarcodeLogic(temp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mBarcodeListAdapter.notifyDataSetChanged();

                    calculateDifference();

                    mBarcode.setText("");
                }
            }
        };
        mBarcode.addTextChangedListener(barcodeTextWatcher);
    }

    public void calculateDifference() {
        int difference, temp;
        mCount.setText(String.valueOf(mCountGlobal));

        if(mPreCount.getText().toString() == "") {
            mPreCountGlobal = 0;
        }
        //count = Integer.parseInt(mCount.getText().toString());

        difference = mCountGlobal - mPreCountGlobal;
        // if 0, set difference text to green
        // Otherwise set text Colour to red
        if(difference == 0) {
            mDifference.setTextColor(Color.GREEN);
        } else {
            mDifference.setTextColor(Color.RED);
        }
        mDifference.setText(Integer.toString(difference));
    }

    // Contructs new barcode object and adds it to the arraylist
    // Not sure whether to do processing here or in barcode class
    // Processing currently done in barcode class
    public void addBarcodeLogic (String barcode) throws Exception {
        //Check to see if the barcode doesn't exist before adding.
        boolean unique = true;
        for (int i = 0; i < getAreaOnFromPassedInstance().getBarcodeList().size(); i++){
            if (getAreaOnFromPassedInstance().getBarcodeList().get(i).getBarcode().equals(barcode)){
                unique = false;
                break;
            }
        }

        if (unique) {
            mCountGlobal++;
            getAreaOnFromPassedInstance().addBarcode(new Barcode(barcode, getAreaOnFromPassedInstance().getAreaName()));
        } else {
            Toast.makeText(this, "You have scanned a barcode twice, we are not entering it into the system", Toast.LENGTH_LONG).show();
        }
    }

    public void DeleteRow(View view) throws Exception {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();
        Log.i(TAG, "DeleteRow: item ");
        Toast.makeText(this, item +" deleted", Toast.LENGTH_LONG).show();
        for (int i=0;i <getAreaOnFromPassedInstance().getBarcodeList().size();i++){
            if (getAreaOnFromPassedInstance().getBarcodeList().get(i).getBarcode().equals(item)){
                getAreaOnFromPassedInstance().getBarcodeList().remove(i);
                view.getId();
                mBarcodeListAdapter.notifyDataSetChanged();
                mListView.invalidateViews();
                break;
            }

        }
    }

    public void BackHandler(View view) {
        Intent intents = new Intent(ScanningScreen.this, AreasScreen.class);
        startActivity(intents);
    }
}