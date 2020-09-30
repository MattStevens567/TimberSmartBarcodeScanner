package com.example.timbersmartbarcodescanner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

/*
* This is a singleton class which will hold all the data for each stock take.
* This was decided to avoid passing bundles across many activities.
* */
public class Data implements Serializable {


    private static final String FILENAME = "notused.txt";
    private static String FILE_NAME = "timbersmart.txt";
    private static Data mData = null;
    private ArrayList<Stocktake> mStocktakeList = new ArrayList<Stocktake>();

    public String ToString() {
        StringBuilder data;
        data = new StringBuilder("\"START-OF-TIMBER-SMART-DATA\"");
        for (int i=0; i<mStocktakeList.size(); i++) {
            data.append("\"Stock-take-start\"");
            data.append("\"").append(mStocktakeList.get(i).getStocktakeString()).append("\"");
            data.append("\"").append(mStocktakeList.get(i).getDateCreated()).append("\"");
            data.append("\"").append(mStocktakeList.get(i).getDateModified()).append("\"");
            for (int j = 0; j<mStocktakeList.get(i).getAreaList().size(); j++){
                data.append("\"Area-start\"");
                data.append("\"").append(mStocktakeList.get(i).getAreaList().get(j).getAreaString()).append("\"");
                data.append("\"").append(mStocktakeList.get(i).getAreaList().get(j).getDate()).append("\"");
                for (int k = 0; k<mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().size(); k++){
                    data.append("\"Barcode-start\"");
                    data.append("\"").append(mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().get(k).getBarcode()).append("\"");
                    data.append("\"").append(mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().get(k).getDateTime()).append("\"");
                    data.append("\"").append(mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().get(k).getArea()).append("\"");
                    data.append("\"Barcode-end\"");
                }
                data.append("\"Area-end\"");
            }
            data.append("\"Stock-take-end\"");
        }
        data.append("\"END-OF-TIMBER-SMART-DATA\"");
        return data.toString();
    }

    public void setStocktakeList(ArrayList<Stocktake> stocktakeList) {
        mStocktakeList = stocktakeList;
    }

    public void addStocktake(Stocktake stocktake) {
        mStocktakeList.add(stocktake);
    }


    private Data(ArrayList<Stocktake> stocktakeList){
        mStocktakeList = stocktakeList;
    }

    private Data(){
        if (mData == null) mStocktakeList = new ArrayList<Stocktake>();
    }

    public static Data getDataInstance(ArrayList<Stocktake> stocktakeList){
        if (mData == null){
            mData = new Data(stocktakeList);
            return mData;
        }
        else {
//            mData.mStocktakeList = stocktakeList;
            return mData;
        }
    }

    public static Data getDataInstance() throws Exception {
        if (mData == null){
            mData = new Data();
        }
        return mData;
    }
//    public static void initialize() throws Exception {
//        if (mData.easyRead()){
//        } else {
//            mData = new Data();
//        }
//    }

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
            data += "\"" + mStocktakeList.get(i).getDateCreated() + "\"";
            data += "\"" + mStocktakeList.get(i).getDateModified() + "\"";
            for (int j = 0; j<mStocktakeList.get(i).getAreaList().size(); j++){
                data += "\"Area-start\"";
                data += "\"" + mStocktakeList.get(i).getAreaList().get(j).getAreaString() + "\"";
                data += "\"" + mStocktakeList.get(i).getAreaList().get(j).getDate() + "\"";
                for (int k = 0; k<mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().size(); k++){
                    data += "\"Barcode-start\"";
                    data += "\"" + mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().get(k).getBarcode() + "\"";
                    data += "\"" + mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().get(k).getDateTime() + "\"";
                    data += "\"" + mStocktakeList.get(i).getAreaList().get(j).getBarcodeList().get(k).getArea() + "\"";
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

//    public boolean easySave(){
//        FileOutputStream fos;
//        ObjectOutputStream oos=null;
//        try{
////            fos = getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//            oos = new ObjectOutputStream(fos);
//            oos.writeObject(mStocktakeList);
//            oos.close();
//            return true;
//        }catch(Exception e){
//            Log.e("Internal Stocktake Save", "Cant save records"+e.getMessage());
//            return false;
//        }
//        finally{
//            if(oos!=null)
//                try{
//                    oos.close();
//                }catch(Exception e){
//                    Log.e("Internal Stocktake Save", "Error while closing stream "+e.getMessage());
//                }
//        }
//    }

    public void reallyEasySave(Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(this);
        os.close();
        fos.close();
    }
    public void reallyEasyRead(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(FILE_NAME);
        ObjectInputStream is = new ObjectInputStream(fis);
        mData = (Data) is.readObject();
        is.close();
        fis.close();
    }
//    public boolean easyRead(){
//        FileInputStream fin;
//        ObjectInputStream ois=null;
//        try{
//            fin = getApplicationContext().openFileInput(FILE_NAME);
//            ois = new ObjectInputStream(fin);
//            this.mStocktakeList = (ArrayList<Stocktake>) ois.readObject();
//            ois.close();
//            Log.v("Internal Stocktake Save", "Records read successfully");
//            return true;
//        }catch(Exception e){
//            Log.e("Internal Stocktake Save", "Cant read saved records"+e.getMessage());
//            return false;
//        }
//        finally{
//            if(ois!=null)
//                try{
//                    ois.close();
//                }catch(Exception e){
//                    Log.e("Internal Stocktake Save", "Error in closing stream while reading records"+e.getMessage());
//                }
//        }
//    }

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
