package com.example.timbersmartbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mButtonClear;
    private TextView mEditTextBarcode, mTextViewBarcodesScanned;
    static private int barcodesScanned = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("test this is a test this is what a test has");

        mEditTextBarcode = findViewById(R.id.editTextBarcode);
        mEditTextBarcode.setText("");
        mTextViewBarcodesScanned = findViewById(R.id.textViewBarCodesScannedFill);


        mButtonClear = findViewById(R.id.buttonClear);
        mButtonClear.setOnClickListener((View v) -> {
            mEditTextBarcode.setText("");
            barcodesScanned++;
            mTextViewBarcodesScanned.setText(String.valueOf(barcodesScanned));
        });
    }
}