package com.example.timbersmartbarcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StockTakeListAdapter extends ArrayAdapter {
    private static final String TAG = "StockTakeListAdapter";
    private Context mContext;
    private int mResource;

    /**
     *   Default Constructor for the StockTakeListAdapter
      *  @param context
      *  @param resource
      *  @param objects
     */
    public StockTakeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Stocktake> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stocktake stocktake  = (Stocktake) getItem(position);
        String stocktakeName = stocktake.getmStringStockTakeName();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);



        TextView textViewStockTakeName = convertView.findViewById(R.id.ActivityMainTextViewStockTakeName);
        textViewStockTakeName.setText(stocktakeName);
        return convertView;
    }
}
