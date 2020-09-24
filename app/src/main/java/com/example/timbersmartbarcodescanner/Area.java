package com.example.timbersmartbarcodescanner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Area implements Serializable {
    private ArrayList<Barcode> mBarcodeList;
    private String mArea, mDate;
    private int mPreCount;


    public Area(String area) {
        this.mArea = area;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mDate = formatter.format(date);
        mBarcodeList = new ArrayList<>();
        mPreCount = 0;
    }

    public String getAreaName() { return mArea; }
    public void setAreaName(String mArea) {
        this.mArea = mArea;
    }

    public String getDate() {
        return mDate;
    }
    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public ArrayList<Barcode> getBarcodeList() {
        return mBarcodeList;
    }

    public void setBarcodeList(ArrayList<Barcode> barcodeList) {
        mBarcodeList = barcodeList;
    }

    public void addBarcode(Barcode barcode) {
        mBarcodeList.add(barcode);
    }

    public int getPreCount() {
        return mPreCount;
    }

    public void setPreCount(int preCount) {
     mPreCount = preCount;
    }
}
