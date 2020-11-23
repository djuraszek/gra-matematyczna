package com.android.gramatematyczna;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

public class DrawingsGridAdapter  extends BaseAdapter {
    Context context;
    int drawings[];
    LayoutInflater inflter;

    public DrawingsGridAdapter(Context applicationContext, int[] drawings) {
        this.context = applicationContext;
        this.drawings = drawings;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return drawings.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_grid_drawings, null); // inflate the layout
        CardView card = (CardView) view.findViewById(R.id.cardview); // get the reference of ImageView
        Drawable drawing = context.getDrawable(drawings[i]);
        ImageView background = (ImageView)view.findViewById(R.id.card_background);
        ImageView lock = (ImageView)view.findViewById(R.id.item_lock);
        ImageView download = (ImageView)view.findViewById(R.id.item_unlock);
        background.setBackground(drawing); // set logo images
//        card.setCardBackgroundColor(drawing); // set logo images
        return view;
    }
}