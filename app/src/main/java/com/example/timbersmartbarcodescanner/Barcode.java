package com.example.timbersmartbarcodescanner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Barcode implements Serializable {
    private String mBarcode = "";
    private String mDateTime = "";
    private String mArea = "";

    //Constructors
    public Barcode(String mBarcode, String mArea) {
        this.mBarcode = mBarcode;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        this.mDateTime = formatter.format(date);
        this.mArea = mArea;
    }

    public Barcode(String mBarcode) {
        this.mBarcode = mBarcode;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        this.mDateTime = formatter.format(date);
        this.mArea = "Temp Area";
    }

//    public Barcode(String mBarcode, String mDateTime) {
//        this.mBarcode = mBarcode;
//        this.mDateTime = mDateTime;
//
//    }

    public Barcode() {
        this.mBarcode = "Temp Barcode";
        this.mDateTime = "Test Date Time";
    }

    public String getArea() {
        return mArea;
    }

    public void setArea(String area) {
        mArea = area;
    }

    // Getters and setters
    public String getBarcode() {
        return mBarcode;
    }

    public void setBarcode(String barcode) {
        mBarcode = barcode;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }
}
