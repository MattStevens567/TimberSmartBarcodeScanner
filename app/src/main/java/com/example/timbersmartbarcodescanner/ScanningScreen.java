package com.example.timbersmartbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;

public class ScanningScreen extends Activity {

    private static final String TAG = "ScanningScreen";

    private int mCountGlobal;

    private TextView mCount, mDifference;
    private EditText mBarcode, mPreCount;
    private Button mEnter;
    private ListView mListView;


    private BarcodeListAdapter mBarcodeListAdapter;
    private ArrayList<Barcode> sampleBarcodes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_screen);

        Log.d(TAG, "onCreate: Started");

        init();


    }

    public void init() {

        // Assigning views to variables
        mCount = findViewById(R.id.textViewCount);
        mDifference = findViewById(R.id.textViewDifference);
        mBarcode = findViewById(R.id.editTextBarcode);
        mPreCount = findViewById(R.id.editTextPreCount);
        mEnter = findViewById(R.id.buttonEnter);
        mListView = findViewById(R.id.ScanningScreenListView);

        // Set this to be the value passed from area
        // or just count how many barcodes are currently in array
        mCountGlobal = 0;
        mCount.setText(String.valueOf(mCountGlobal));

        mDifference.setText("0");


        // Some Test Data for the meantime =============================
        sampleBarcodes = new ArrayList<>();
//        for (int i = 0; i < 50; i++){
//            int intBarcodeDetail = i*i*1232%10000;
//            String barcodeDetail = Integer.toString(intBarcodeDetail);
//            sampleBarcodes.add(new Barcode(barcodeDetail));
//        }
        // =============================================================
        mBarcodeListAdapter = new BarcodeListAdapter(this, R.layout.scanning_screen_listview_layout, sampleBarcodes);
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
                CharSequence text = "Enter Pressed";
                Log.d(TAG, "YOLOLOLOO");
                Context context = getApplicationContext();
                int duration  = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            mBarcode.setText(temp);
            //update();
        });


        initTextWatchers();

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
                Log.d(TAG, "ya yeet");
                if(temp.contains("\n")) {
                    mCountGlobal++;

                    // Only shorten barcode if it had more then just \n
                    if(temp.length() > 2) {
                        temp = temp.substring(0, temp.length() - 2);
                    }

                    CharSequence text = "Barcode " + temp + " found";
                    Log.d(TAG, text.toString());
                    Context context = getApplicationContext();
                    int duration  = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    String barcodeString = mBarcode.getText().toString();
                    // Send Barcode string to addBarcodeLogic function
                    // This function handles DateTime etc. to create barcode object
                    addBarcodeLogic(barcodeString);
                    mBarcodeListAdapter.notifyDataSetChanged();

                    calculateDifference();

                    mBarcode.setText("");
                }
            }
        };
        mBarcode.addTextChangedListener(barcodeTextWatcher);

        // preCountTextWatcher
        TextWatcher preCountTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Unused
                String temp = charSequence.toString();
                if(temp.length() <= 0) {
                    mPreCount.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable preCount) {
                // Updates difference when preCount is changed
                // Difference also updates if the count increases (this happens elsewhere in code)
                calculateDifference();
            }
        };

        // Attach TextWatchers to editTextViews

        mPreCount.addTextChangedListener(preCountTextWatcher);
    }

    public void calculateDifference() {
        int difference, temp;
        mCount.setText(String.valueOf(mCountGlobal));

        if(mPreCount.getText().toString() == "") {
            temp = 0;
        } else {
            temp = Integer.parseInt(mPreCount.getText().toString());
        }
        //count = Integer.parseInt(mCount.getText().toString());

        difference = mCountGlobal - temp;
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
    public void addBarcodeLogic (String barcode) {
        sampleBarcodes.add(new Barcode (barcode));
    }

//    public void update(){
//        //ListView mListView = findViewById(R.id.ScanningScreenListView);
//
//        BarcodeListAdapter update = new BarcodeListAdapter(this, R.layout.scanning_screen_listview_layout, sampleBarcodes);
//        //barcodeListAdapter.notifyDataSetChanged();
//        mListView.setAdapter(update);
//    }

    public void DeleteRow(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView child = (TextView)parent.getChildAt(0);
        String item = child.getText().toString();

        Toast.makeText(this, item +" deleted", Toast.LENGTH_LONG).show();
        for (int i=0;i <sampleBarcodes.size();i++){
            if (sampleBarcodes.get(i).getmBarcode().equals(item)){
                sampleBarcodes.remove(i);
                final ListView mListView = findViewById(R.id.rowListView);
                mBarcodeListAdapter.notifyDataSetChanged();

                return;
            }

        }
    }





}