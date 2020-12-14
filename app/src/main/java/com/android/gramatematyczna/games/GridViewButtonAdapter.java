package com.android.gramatematyczna.games;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by B.A.Wędrychowicz on 24.11.2020
 */
class GridViewButtonAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public GridViewButtonAdapter(Context c, Integer[] mThumbIds) {
        mContext = c;
        this.mThumbIds=mThumbIds;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds;
//            = {
//            R.drawable.dot_full,
//            R.drawable.dot_full,
//            R.drawable.dot_full,
//            R.drawable.dot_full,
//            R.drawable.dot_full,
//    };
}