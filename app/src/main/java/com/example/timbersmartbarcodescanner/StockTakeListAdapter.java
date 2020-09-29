package com.example.timbersmartbarcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StockTakeListAdapter extends ArrayAdapter {
    private static final String TAG = "StockTakeListAdapter";
    private Context mContext;
    private int mResource;
    private ArrayList<Stocktake> data;

    public StockTakeListAdapter(@NonNull Context context, int resource, ArrayList<Stocktake> stocktakes) {
        super(context, resource, stocktakes);
        this.mContext = context;
        this.mResource = resource;
        this.data = stocktakes;
    }

    public StockTakeListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        try {
            if (!Data.getDataInstance().getStocktakeList().isEmpty())
                return Data.getDataInstance().getStocktakeList().size();
            else return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stocktake stocktake  = (Stocktake) getItem(position);
        String stocktakeName = stocktake.getStocktakeString();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);



        TextView textViewStockTakeName = convertView.findViewById(R.id.ActivityMainTextViewStockTakeName);
        textViewStockTakeName.setText(stocktakeName);
        return convertView;
    }
}
