//package com.example.timbersmartbarcodescanner;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.List;
//import java.util.Vector;
//
//public class AddScanScreen extends AppCompatActivity {
//    /*
//    TO DO:
//    - When a barcode is scanned, it enters automatically (no button needed)
//        (need to enable continous barcode by scanning the barcode tyron found (cocode))
//
//    - Unique barcodes only (either scan or implement)
//           when you scan
//            check if it's in the vector
//            if not in vector (add)
//            else return
//
//    - Add a location ID (
//        Struct BarcodeAndLocation {
//         string location,
//         string barcode;
//        }
//
//     when you scan
//        check if it's in the vector
//        if not in vector (add)
//        else return
//
//     */
//
//    private Button mButtonClear;
//    private Button mButtonReset;
//    private Vector<String> mVectorStringBarcodesScanned = new Vector<String>();
//    private TextView mEditTextBarcode, mTextViewBarcodesScanned;
//    private TextView mTextViewBarcodesAlreadyScanned ;
//    static private int barcodesScanned = 0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mButtonReset = findViewById(R.id.buttonReset);
//        mTextViewBarcodesAlreadyScanned = findViewById(R.id.textViewPrevScans);
//        mEditTextBarcode = findViewById(R.id.editTextBarcode);
//        mEditTextBarcode.setText("");
//        mTextViewBarcodesAlreadyScanned.setText("");
//        mTextViewBarcodesScanned = findViewById(R.id.textViewBarCodesScannedFill);
//
//
//        mButtonClear = findViewById(R.id.buttonClear);
//        mButtonClear.setOnClickListener((View v) -> {
//            barcodesScanned++;
//            String stringBarcodeScanned = "Number of barcodes scanned: " + String.valueOf(barcodesScanned);
//            mTextViewBarcodesScanned.setText(stringBarcodeScanned);
//            mVectorStringBarcodesScanned.add(mEditTextBarcode.getText().toString());
//            //Really bad code for the mean-time
//            mTextViewBarcodesAlreadyScanned.setText(mTextViewBarcodesAlreadyScanned.getText()+ "\n"
//                    + mEditTextBarcode.getText().toString());
//            mEditTextBarcode.setText("");
//        });
//        mButtonReset.setOnClickListener((View v) ->  {
//            mVectorStringBarcodesScanned.clear();
//            mTextViewBarcodesAlreadyScanned.setText("");
//            mTextViewBarcodesScanned.setText("");
//            barcodesScanned = 0;
//        });
//    }
//}