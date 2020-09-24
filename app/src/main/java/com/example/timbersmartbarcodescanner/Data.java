package com.example.timbersmartbarcodescanner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
* This is a singleton class which will hold all the data for each stock take.
* This was decided to avoid passing bundles across many activities.
* */
public class Data extends Activity {

    private static final String FILENAME = "BarcodeStickScannerData";
    private static Data mData = null;

    public void setStocktakeList(ArrayList<Stocktake> mStocktakeList) {
        this.mStocktakeList = mStocktakeList;
    }
    public void addToStocktakeList(Stocktake stocktake) {
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

    public ArrayList<Stocktake> getStocktakeList() {
        return mStocktakeList;
    }

    public Stocktake getStockTake(int i) {
        return mStocktakeList.get(i);
    }


    /*
     *       Rules for saving data:
     *       START-OF-TIMBER-SMART-DATA
     *       STOCKTAKE-START
     *       "StockTake 1"
     *       "StockTake Start Date"
     *       "StockTake Most Recent Edit"
     *       AREAS-START
     *       "Area 1 Name"
     *       "Area 1 Date"
     *       BARCODE-START
     *       "BarcodeValue"
     *       "BarcodeDate"
     *       "BardcodeArea"
     *       BARCODE-END
     *       AREAS-END
     *       STOCKTAKE-END
     *       END-OF-TIMBER-SMART-DATA
     */
    public void saveToDataToLocalFile(Context context){
        String data;
        data = "\"START-OF-TIMBER-SMART-DATA\"";
        for (int i=0; i<mStocktakeList.size(); i++) {
            data += "\"Stock-take-start\"";
            data += "\"" + mStocktakeList.get(i).getmStartDate() + "\"";
            data += "\"" + mStocktakeList.get(i).getmMostRecentEdit() + "\"";
            for (int j=0; j<mStocktakeList.get(i).getmStockTakeAreas().size(); j++){
                data += "\"Area-start\"";
                data += "\"" + mStocktakeList.get(i).getmStockTakeAreas().get(j).getAreaName() + "\"";
                data += "\"" + mStocktakeList.get(i).getmStockTakeAreas().get(j).getDate() + "\"";
                for (int k=0; k<mStocktakeList.get(i).getmStockTakeAreas().get(j).getBarcodeList().size(); k++){
                    data += "\"Barcode-start\"";
                    data += "\"" + mStocktakeList.get(i).getmStockTakeAreas().get(j).getBarcodeList().get(k).getBarcode() + "\"";
                    data += "\"" + mStocktakeList.get(i).getmStockTakeAreas().get(j).getBarcodeList().get(k).getDateTime() + "\"";
                    data += "\"" + mStocktakeList.get(i).getmStockTakeAreas().get(j).getBarcodeList().get(k).getArea() + "\"";
                    data += "\"Barcode-end\"";
                }
                data += "\"Area-end\"";
            }
            data += "\"Stock-take-end\"";
        }
        data = "\"END-OF-TIMBER-SMART-DATA\"";
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /*
     * Rules for loading in data from a file
     *
     *  While we haven't read "TIMBER-SMART-DATA-EOF" string
     *  READ IN STOCK TAKES (maybe a string that says "LOAD STOCKTAKE"
     *   First Item
     *
     *   Example of a saved file:
     *
     *  START-OF-TIMBER-SMART-DATA
     *   STOCKTAKE-START
     *       "StockTake 1"
     *       "StockTake Start Date"
     *       "StockTake Most Recent Edit"
     *           AREAS-START
     *           "Area 1 Name"
     *           "Area 1 Date"
     *               BARCODE-START
     *               "BarcodeValue"
     *               "BarcodeDate"
     *               "BardcodeArea"
     *               BARCODE-END
     *           AREAS-END
     *   STOCKTAKE-END
     *  END-OF-TIMBER-SMART-DATA
     *
     *       Area1Date
     *           BARCODE-START
     *
     *
     *
     */
    public void loadDataFromLocalFile(){

    }

}
