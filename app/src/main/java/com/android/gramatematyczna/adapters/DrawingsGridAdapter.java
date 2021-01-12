package com.android.gramatematyczna.adapters;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.activities.AchievementsActivity;
import com.android.gramatematyczna.classes.Drawing;
import com.android.gramatematyczna.customdialogs.DialogShowCode;
import com.android.gramatematyczna.database.DatabaseHelper;

import java.util.ArrayList;

public class DrawingsGridAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<Drawing> drawings;
    LayoutInflater inflater;

    boolean canUnlock = false;

    public DrawingsGridAdapter(Activity applicationContext, ArrayList<Drawing> drawings, boolean canUnlock) {
        this.activity = applicationContext;
        this.drawings = drawings;
        this.canUnlock = canUnlock;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return drawings.size();
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
        final Drawing drawing = drawings.get(i);
        final int id = i;
        view = inflater.inflate(R.layout.item_grid_drawings, null); // inflate the layout
        CardView card = (CardView) view.findViewById(R.id.cardview); // get the reference of ImageView
        boolean unlocked = drawing.isUnlocked();
        byte[] b = drawing.getImg();
        final String code = drawing.getCode();
        //todo
        if (b != null) {
//            System.out.println("Drawing " + i + " not null");
            Drawable drawable = new BitmapDrawable(BitmapFactory.decodeByteArray(b, 0, b.length));
            ImageView background = (ImageView) view.findViewById(R.id.card_background);
            background.setBackground(drawable); // set logo images
        } else {
//            Log.e("DrawingsGridAdapter", "Drawing " + i + " is null");
//            System.out.println("Drawing " + i + " is null");
        }

        ImageView lock = (ImageView) view.findViewById(R.id.item_lock);
        ImageView download = (ImageView) view.findViewById(R.id.item_unlock);

        if (unlocked) {
            lock.setVisibility(View.INVISIBLE);
            download.setVisibility(View.INVISIBLE);
            //todo on click show code
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogShowCode dialog = new DialogShowCode(activity, code);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                }
            });
        } else if (!unlocked && canUnlock) {
            //jest zablokowane, ale mozna odblokowac
            lock.setVisibility(View.INVISIBLE);
            download.setVisibility(View.VISIBLE);
            //todo on click show code
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo odblokuj kolorowanke
//                    System.out.println("id " + id);
                    setDrawingLocked(id);
                }
            });
        } else {
            //jest zablokowane i nie mozna odblokowac
            lock.setVisibility(View.VISIBLE);
            download.setVisibility(View.INVISIBLE);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playCantUnlockSound();
                }
            });
        }
        return view;
    }

    public void setDrawingLocked(int index) {
        drawings.get(index).setUnlocked(true);
        //zmien serduszka
        PreferencesManagement preferences = new PreferencesManagement(activity);
        preferences.clearHearts();
        DatabaseHelper dbHelper = new DatabaseHelper(activity);
        dbHelper.updateDrawing(drawings.get(index).getId());

        notifyDataSetChanged();
        updatedGrid();
    }

    public void updatedGrid(){
//        System.out.println("DrawingsGridAdapter: updateGrid()");
        ((AchievementsActivity)activity).unlockedPicture();
    }

    public void playCantUnlockSound(){
        ((AchievementsActivity)activity).playSound(false);
    }


}