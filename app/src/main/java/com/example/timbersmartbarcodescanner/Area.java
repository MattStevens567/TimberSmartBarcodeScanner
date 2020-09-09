package com.example.timbersmartbarcodescanner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Area {
    private String mArea, mDate;


    public Area(String nArea) {
        this.mArea = nArea;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        mDate = formatter.format(date);
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
}
