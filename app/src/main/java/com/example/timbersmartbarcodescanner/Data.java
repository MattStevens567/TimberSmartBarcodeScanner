package com.example.timbersmartbarcodescanner;

import java.util.ArrayList;

/*
* This is a singleton class which will hold all the data for each stock take.
* This was decided to avoid passing bundles across many activities.
* */
public class Data {
    private static Data mData = null;

    public ArrayList<Stocktake> getmStocktakeList() {
        return mStocktakeList;
    }

    public void setmStocktakeList(ArrayList<Stocktake> mStocktakeList) {
        this.mStocktakeList = mStocktakeList;
    }
    public void addTomStocktakeList(Stocktake stocktake) {
        this.mStocktakeList.add(stocktake);
    }

    private ArrayList<Stocktake> mStocktakeList;
    private Data(ArrayList<Stocktake> stocktakeList){
        this.mStocktakeList = stocktakeList;
    }

    public static Data getDataInstance(ArrayList<Stocktake> stocktakeList){
        if (mData == null){
            mData = new Data(stocktakeList);
            return mData;
        }
        else {
            return mData;
        }
    }

    public static Data getDataInstance() throws Exception {
        if (mData == null){
            throw new Exception("You have tried to get the mData, however it is null");
        }
        return mData;
    }
}