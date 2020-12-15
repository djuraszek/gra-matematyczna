package com.android.gramatematyczna.games;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.android.gramatematyczna.R;

/**
 * Created by B.A.Wędrychowicz on 24.11.2020
 */
class GridViewImageAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds;
    LayoutInflater inflater;

    // Constructor
    public GridViewImageAdapter(Context c, Integer[] mThumbIds) {
        mContext = c;
        this.mThumbIds = mThumbIds;
        inflater  = (LayoutInflater.from(c));
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
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
////            imageView.set
//            imageView.setPadding(8, 8, 8, 8);
//        }
//        else{
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
//    }


//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
////            imageView.set
//            imageView.setPadding(8, 8, 8, 8);
//        }
//        else{
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
//    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_grid_images, null); // inflate the layout
        ImageView background = (ImageView)view.findViewById(R.id.card_background);
        background.setImageResource(mThumbIds[i]);
        return view;
    }
}