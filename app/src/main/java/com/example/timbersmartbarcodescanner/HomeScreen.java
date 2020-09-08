package com.example.timbersmartbarcodescanner;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.service.quicksettings.Tile;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity {
    private TileAdapter mBarcodeEntryAdapter;
    private final int NUMBEROFBARCODESCANS = 3;
    private final int NCOLS = 1;
    private GridView mBarcodeEntries;
    public class TileAdapter extends BaseAdapter {
        class ViewHolder {
            int position;
            String text;
        }
        // how many tiles
        @Override
        public int getCount() {
            return NUMBEROFBARCODESCANS;
        }
        // not used
        @Override
        public Object getItem(int i) {
            return null;
        }
        // not used
        @Override
        public long getItemId(int i) {
            return i;
        }

        // populate a view
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder vh;
            //ImageView image;
            if (convertView == null) {
                // if it's not recycled, inflate it from xml
                convertView = getLayoutInflater().inflate(R.layout.barcodentry,  viewGroup, false);
                // convertview will be a LinearLayout
                vh=new ViewHolder();
                vh.text= "hello2";
                // and set the tag to it
                convertView.setTag(vh);
            } else
                vh=(ViewHolder)convertView.getTag();
            // set size to be square
            convertView.setMinimumHeight(mBarcodeEntries.getWidth() /  mBarcodeEntries.getNumColumns());
            // make sure it isn't rotated
            vh.text = "Hello this is a barcode entry";
            vh.position=i;

            return convertView;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.plusButtonBottomRight);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, AddScanScreen.class));
            }
        });

        mBarcodeEntries = findViewById(R.id.gridView);
        mBarcodeEntries.setNumColumns(NCOLS);
        mBarcodeEntryAdapter = new TileAdapter();
    }
}