package com.model.marker_model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.hl3hl3.arcoremeasure.R;

public class ImageAdapter  extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.p_logo, R.drawable.lock,
            R.drawable.ic_drop_down, R.drawable.logo,
            R.drawable.nic_card, R.drawable.adress,
            R.drawable.mobile, R.drawable.car_model,
            R.drawable.nic_card, R.drawable.car_name,
            R.drawable.user_white, R.drawable.flash,
            R.drawable.plogo, R.drawable.email
    };

    // Constructor
    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        return imageView;
    }

}