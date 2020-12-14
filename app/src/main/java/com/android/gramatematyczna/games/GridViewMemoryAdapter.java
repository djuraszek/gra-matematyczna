package com.android.gramatematyczna.games;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by B.A.Wędrychowicz on 24.11.2020
 */
class GridViewMemoryAdapter extends BaseAdapter {
    private Context mContext;
    public Integer[] mThumbIds;
    public List<MemoryCard> elements = new ArrayList<>();

    // Constructor
    public GridViewMemoryAdapter(Context c, List<MemoryCard> elements) {
        mContext = c;
//        this.mThumbIds=mThumbIds;
        this.elements=elements;
    }
    public int getCount() {
        return elements.size();
    }
    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 8, 8, 8);
        }
        else{
            imageView = (ImageView) convertView;
        }
        if(elements.get(position).isOpen()){
            imageView.setImageResource(elements.get(position).getPictureID());
        }else {
            imageView.setImageResource(elements.get(position).getPictureBack());
        }
        return imageView;
    }



}