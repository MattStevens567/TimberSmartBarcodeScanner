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

public class BarcodeListAdapter extends ArrayAdapter {
    private static final String TAG = "BarcodeListAdapter";
    private Context mContext;
    private int mResource;

    /**
     *   Default Constructor for the StockTakeListAdapter
     *  @param context
     *  @param resource
     *  @param objects
     */
    public BarcodeListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Barcode> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

        }
        Barcode barcode = (Barcode) getItem(position);
        String barcodeDetails = barcode.getBarcode();
        String dateTime = barcode.getDateTime();
        String area = barcode.getArea();
        TextView textViewDate = convertView.findViewById(R.id.SSLVDate);
        textViewDate.setText(dateTime);

//        TextView textViewTime = convertView.findViewById(R.id.SSLVTime);
//        textViewTime.setText(dateTime);

        TextView textViewBarcode = convertView.findViewById(R.id.SSLVBarcode);
        textViewBarcode.setText(barcodeDetails);

        TextView textViewArea = convertView.findViewById(R.id.SSLVArea);
        textViewArea.setText(area);

        return convertView;
    }
}
