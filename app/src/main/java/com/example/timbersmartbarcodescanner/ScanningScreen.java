package com.example.timbersmartbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ScanningScreen extends Activity {

    private static final String TAG = "ScanningScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_screen);

        Log.d(TAG, "onCreate: Started");
        ListView mListView = findViewById(R.id.ScanningScreenListView);
        //Some Test Data for the meantime
        ArrayList<Barcode> sampleBarcodes = new ArrayList<>();
        for (int i = 0; i < 50; i++){
            int intBarcodeDetail = i*i*1232%10000;
            String barcodeDetail = Integer.toString(intBarcodeDetail);
            sampleBarcodes.add(new Barcode(barcodeDetail));
        }

        BarcodeListAdapter barcodeListAdapter = new BarcodeListAdapter(this, R.layout.scanning_screen_listview_layout, sampleBarcodes);
        mListView.setAdapter(barcodeListAdapter);
    }
}