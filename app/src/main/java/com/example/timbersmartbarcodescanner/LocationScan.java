package com.example.timbersmartbarcodescanner;

import java.util.Vector;

public class LocationScan {
    public String mStringLocation;
    public Vector<String> mVectorOfStringsBarcodesInALocation;

    public LocationScan(){
        mStringLocation = "";
        mVectorOfStringsBarcodesInALocation = new Vector<String>();
    }

    public LocationScan(String loc, Vector<String> barcodes){
        mStringLocation = loc;
        mVectorOfStringsBarcodesInALocation = barcodes;
    }
}


