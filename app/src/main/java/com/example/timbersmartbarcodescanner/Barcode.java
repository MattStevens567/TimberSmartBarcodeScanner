package com.example.timbersmartbarcodescanner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Barcode {
    private String mBarcode;
    private String mDateTime;
    private String mArea;

    //Constructors
    public Barcode(String mBarcode, String mDateTime, String mArea) {
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

    public Barcode(String mBarcode, String mDateTime) {
        this.mBarcode = mBarcode;
        this.mDateTime = mDateTime;

    }

    public Barcode() {
        this.mBarcode = "Temp Barcode";
        this.mDateTime = "Test Date Time";
    }

    public String getmArea() {
        return mArea;
    }

    public void setmArea(String mArea) {
        this.mArea = mArea;
    }

    // Getters and setters
    public String getmBarcode() {
        return mBarcode;
    }

    public void setmBarcode(String mBarcode) {
        this.mBarcode = mBarcode;
    }

    public String getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(String mDateTime) {
        this.mDateTime = mDateTime;
    }
}
