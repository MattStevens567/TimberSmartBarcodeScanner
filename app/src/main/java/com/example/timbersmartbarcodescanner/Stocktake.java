package com.example.timbersmartbarcodescanner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.Vector;

public class Stocktake implements Serializable{
    private String mStringStockTakeName = "";
    private ArrayList<Area> mStockTakeAreas = new ArrayList<Area>();
    private String mStartDate, mMostRecentEdit= "";

    // Constructors
    public Stocktake(String mStringStockTakeName) {
        this.mStringStockTakeName = mStringStockTakeName;
        mStockTakeAreas = new ArrayList<>() ;
    }

    public Stocktake(String mStringStockTakeName, ArrayList<Area> stocktakeAreas) {
        this.mStringStockTakeName = mStringStockTakeName;
        this.mStockTakeAreas = stocktakeAreas;
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
    public void setmStringStockTakeName(String mStringStockTakeName, ArrayList<Area> areas) {
        this.mStringStockTakeName = mStringStockTakeName;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mStartDate = mMostRecentEdit =  formatter.format(date);
        mStockTakeAreas = areas;
    }


    public ArrayList<Area> getmStockTakeAreas() {
        return mStockTakeAreas;
    }

    public void addmStockTakeAreas(Area mStockTakeAreas) {
        this.mStockTakeAreas.add(mStockTakeAreas);
    }
    public void setmStockTakeAreas(ArrayList<Area> mStockTakeAreas) {
        this.mStockTakeAreas = mStockTakeAreas;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmMostRecentEdit() {
        return mMostRecentEdit;
    }

    public void setmMostRecentEdit(String mMostRecentEdit) {
        this.mMostRecentEdit = mMostRecentEdit;
    }
}
