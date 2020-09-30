package com.example.timbersmartbarcodescanner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Barcode implements Serializable {
    private String mBarcodeString = "";
    private String mDateTime = "";
    private String mArea = "";

    //Constructors
    public Barcode(String barcodeString, String area) {
        mBarcodeString = barcodeString;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mDateTime = formatter.format(date);
        mArea = area;
    }

    public Barcode(String barcode, String date, String area) {
        this.mBarcodeString = barcode;
        this.mDateTime = date;
        this.mArea = area;
    }

//    public Barcode(String barcodeString) {
//        mBarcodeString = barcodeString;
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        Date date = new Date();
//        mDateTime = formatter.format(date);
//        this.mArea = "Temp Area";
//    }

//    public Barcode(String mBarcode, String mDateTime) {
//        this.mBarcode = mBarcode;
//        this.mDateTime = mDateTime;
//
//    }

//    public Barcode() {
//        this.mBarcodeString = "Temp Barcode";
//        this.mDateTime = "Test Date Time";
//    }

    public String getArea() {
        return mArea;
    }

    public void setArea(String area) {
        mArea = area;
    }

    // Getters and setters
    public String getBarcode() {
        return mBarcodeString;
    }

    public void setBarcode(String barcode) {
        mBarcodeString = barcode;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }
}
