package com.example.timbersmartbarcodescanner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiangdg.usbcamera.UVCCameraHelper;
import com.serenegiant.usb.CameraDialog;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.common.AbstractUVCCameraHandler;
import com.serenegiant.usb.widget.CameraViewInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanningScreen extends Activity implements Serializable, CameraDialog.CameraDialogParent, CameraViewInterface.Callback {

    private static final String TAG = "ScanningScreen";

    private int mCountGlobal, mPreCountGlobal;

    private TextView mCount, mDifference;
    private EditText mBarcode, mPreCount;
    private Button mEnter, mConfirmPreCount;
    private ListView mListView;


    private BarcodeListAdapter mBarcodeListAdapter;


    int passedAreaIndex, passedStocktakeIndex;
    //Camera Variables
    private View mTextureView;
    private UVCCameraHelper mCameraHelper;
    private CameraViewInterface mUVCCameraView;

    private boolean isRequest;
    private boolean isPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_screen);

        //grab Area object from AreaScreen
        Intent intent = getIntent();
        passedAreaIndex = intent.getIntExtra("Area Index", -1);
        passedStocktakeIndex = intent.getIntExtra("Stocktake Index", -1);
        mTextureView = findViewById(R.id.TextureViewCamera);
        mUVCCameraView = (CameraViewInterface) mTextureView;
        mUVCCameraView.setCallback(this);
        mCameraHelper = UVCCameraHelper.getInstance();
        mCameraHelper.setDefaultFrameFormat(UVCCameraHelper.FRAME_FORMAT_MJPEG);

        mCameraHelper.initUSBMonitor(this, mUVCCameraView, listener);

//
        mCameraHelper.setOnPreviewFrameListener(new AbstractUVCCameraHandler.OnPreViewResultListener() {
            @Override
            public void onPreviewResult(byte[] nv21Yuv) {
                Log.d(TAG, "onPreviewResult: "+nv21Yuv.length);
            }
        });



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
        mCountGlobal = getAreaOnFromPassedInstance().getBarcodeList().size();
        mPreCountGlobal = getAreaOnFromPassedInstance().getPreCount();

        mCount.setText(String.valueOf(mCountGlobal));
        mPreCount.setHint("Enter PreCount");
        calculateDifference();

        mPreCount.setText(String.valueOf(mPreCountGlobal));



        mBarcodeListAdapter = new BarcodeListAdapter(this, R.layout.scanning_screen_listview_layout, getAreaOnFromPassedInstance().getBarcodeList());
        mListView.setAdapter(mBarcodeListAdapter);

        //update();
        Log.d(TAG, "Reached Line 127!");
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
        Log.d(TAG, "Reached Line 144!");

        mConfirmPreCount.setOnClickListener((View v) -> {
            String tempString = mPreCount.getText().toString();
            int tempPreCount;

            if(tempString.equals("")) {
                tempPreCount = 0;
            } else {
                tempPreCount = Integer.parseInt(tempString);
            }

            mPreCountGlobal = tempPreCount;
            try {
                getAreaOnFromPassedInstance().setPreCount(tempPreCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            calculateDifference();
            mBarcode.requestFocus();

        });

        Log.d(TAG, "Reached Line 167!");
        initTextWatchers();
        Log.d(TAG, "Reached Line 169! XD");
    }

    public Area getAreaOnFromPassedInstance() throws Exception {
        return Data.getDataInstance().getStocktakeList().get(passedStocktakeIndex).getAreaList().get(passedAreaIndex);
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
                    if (temp.length() > 0) {
                        temp = temp.substring(0, temp.length() - 1);
                    }

                    // Check if barcode is empty,
                    // If empty then this means user has pushed enter with nothing entered
                    // Therefore display toast to inform user that barcode hasn't been added
                    if (temp.equals("")) {
                        Toast.makeText(getApplicationContext(), "Attempted to add empty barcode, please check barcode entry box", Toast.LENGTH_SHORT).show();
                        mBarcode.setText("");
                        return;
                    } else {
                        CharSequence text = "Barcode " + temp + " scanned";
                        Log.d(TAG, text.toString());
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
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
            }
        };
        mBarcode.addTextChangedListener(barcodeTextWatcher);
    }

    public void calculateDifference() {
        int difference;
        mCount.setText(String.valueOf(mCountGlobal));

        if(mPreCount.getText().toString() == "") {
            mPreCountGlobal = 0;
        }

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

    // Constructs new barcode object and adds it to the arrayList
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
            getAreaOnFromPassedInstance().addBarcode(new Barcode(barcode, getAreaOnFromPassedInstance().getAreaString()));
        } else {
            Toast.makeText(this, "Barcode ignored, already in system", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: onpause run");
        try {
            writeFileOnInternalStorage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        // step.2 register USB event broadcast
        if (mCameraHelper != null) {
            mCameraHelper.registerUSB();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // step.3 unregister USB event broadcast
        if (mCameraHelper != null) {
            mCameraHelper.unregisterUSB();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // step.4 release uvc camera resources
        if (mCameraHelper != null) {
            mCameraHelper.release();
        }
    }

    public void writeFileOnInternalStorage() throws Exception {
        File path = getApplicationContext().getExternalFilesDir(null);
        File file = new File(path, "my-file-name.txt");
        Log.d(TAG, "writeFileOnInternalStorage: file path: "+ path);
        FileOutputStream stream = new FileOutputStream(file);
        String stringToWriteInFile = Data.getDataInstance().ToString();
        try {
            stream.write(stringToWriteInFile.getBytes());
        } finally {
            stream.close();
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

//    Camera Methods/classes
//    usbCameraActivity Class

    private UVCCameraHelper.OnMyDevConnectListener listener = new UVCCameraHelper.OnMyDevConnectListener() {

        @Override
        public void onAttachDev(UsbDevice device) {
            // request open permission(must have)
            if (!isRequest) {
                isRequest = true;
                if (mCameraHelper != null) {
                    mCameraHelper.requestPermission(0);
                }
            }
        }

        @Override
        public void onDettachDev(UsbDevice device) {
            // close camera(must have)
            if (isRequest) {
                isRequest = false;
                mCameraHelper.closeCamera();
            }
        }

        @Override
        public void onConnectDev(UsbDevice device, boolean isConnected) {
            if (!isConnected) {
                Toast.makeText(ScanningScreen.this,"fail to connect,please check resolution params",Toast.LENGTH_LONG).show();
                isPreview = false;
            } else {
                isPreview = true;
                Toast.makeText(ScanningScreen.this,"connecting",Toast.LENGTH_LONG).show();
                // initialize seekbar
                // need to wait UVCCamera initialize over
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Looper.prepare();
                        Looper.loop();
                    }
                }).start();
            }
        }

        @Override
        public void onDisConnectDev(UsbDevice device) {

        }
    };
    public boolean isCameraOpened() {
        return mCameraHelper.isCameraOpened();
    }

    @Override
    public USBMonitor getUSBMonitor() {
        return mCameraHelper.getUSBMonitor();
    }
    @Override
    public void onDialogResult(boolean canceled) {

    }
    @Override
    public void onSurfaceCreated(CameraViewInterface view, Surface surface) {
        // must have
        if (!isPreview && mCameraHelper.isCameraOpened()) {
            mCameraHelper.startPreview(mUVCCameraView);
            isPreview = true;
        }
    }

    @Override
    public void onSurfaceChanged(CameraViewInterface view, Surface surface, int width, int height) {

    }

    @Override
    public void onSurfaceDestroy(CameraViewInterface view, Surface surface) {
        // must have
        if (isPreview && mCameraHelper.isCameraOpened()) {
            mCameraHelper.stopPreview();
            isPreview = false;
        }
    }
}