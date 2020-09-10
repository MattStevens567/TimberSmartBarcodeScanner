package com.example.timbersmartbarcodescanner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.Vector;

public class Stocktake {
    private String mStringStockTakeName;
    Vector<Area>  mStockTakeAreas;
    String mStartDate, mMostRecentEdit;

    // Constructors
    public Stocktake(String mStringStockTakeName) {
        this.mStringStockTakeName = mStringStockTakeName;
    }

    public Stocktake(String mStringStockTakeName, Vector<Area> stocktakeAreas) {
        this.mStringStockTakeName = mStringStockTakeName;
    }

    // Getters and setters
    public String getmStringStockTakeName() {
        return mStringStockTakeName;
    }

    public void setmStringStockTakeName(String mStringStockTakeName) {
        this.mStringStockTakeName = mStringStockTakeName;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mStartDate = mMostRecentEdit =  formatter.format(date);
    }
    public void setmStringStockTakeName(String mStringStockTakeName, Vector<Area> areas) {
        this.mStringStockTakeName = mStringStockTakeName;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mStartDate = mMostRecentEdit =  formatter.format(date);
        mStockTakeAreas = areas;
    }
}
