package com.example.timbersmartbarcodescanner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Area implements Serializable {
    private ArrayList<Barcode> mBarcodes = new ArrayList<Barcode>();
    private String mArea, mDate = "";


    public Area(String nArea) {
        this.mArea = nArea;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mDate = formatter.format(date);
        mBarcodes = new ArrayList<>() ;
    }

    public String getmAreaName() {

        return mArea;
    }

    public void setAreaName(String mArea) {
        this.mArea = mArea;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public ArrayList<Barcode> getmBarcodes() {
        return mBarcodes;
    }

    public void setmBarcodes(ArrayList<Barcode> mBarcodes) {
        this.mBarcodes = mBarcodes;
    }
    public void addmBarcodes(Barcode mBarcodes) {
        this.mBarcodes.add(mBarcodes);
    }
}
