package com.android.gramatematyczna.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.android.gramatematyczna.AchievementListItem;
import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.games.GameCountActivity;
import com.android.gramatematyczna.games.GameListItem;
import com.android.gramatematyczna.games.GameMemoryActivity;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class DrawingsGridAdapter  extends BaseAdapter {
    Context context;
    int drawings[];
    LayoutInflater inflater;

    public DrawingsGridAdapter(Context applicationContext, int[] drawings) {
        this.context = applicationContext;
        this.drawings = drawings;
        inflater = (LayoutInflater.from(applicationContext));
    }
    AchievementListItem listItems[];
    int hearts = 0;
    PreferencesManagement preferencesManagement;
    public DrawingsGridAdapter(Context applicationContext, AchievementListItem[] listItems) {
        this.context = applicationContext;
        this.listItems = listItems;
        inflater = (LayoutInflater.from(applicationContext));
        preferencesManagement = new PreferencesManagement(context);
        preferencesManagement.manage();
        hearts = preferencesManagement.getHearts();
    }

    @Override
    public int getCount() {
        return listItems.length;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_grid_drawings, null); // inflate the layout
        CardView card = (CardView) view.findViewById(R.id.cardview); // get the reference of ImageView
        Drawable drawing = context.getDrawable(listItems[i].getPicName());
        ImageView background = (ImageView)view.findViewById(R.id.card_background);
        ImageView lock = (ImageView)view.findViewById(R.id.item_lock);
        final ImageView download = (ImageView)view.findViewById(R.id.item_unlock);
        background.setBackground(drawing); // set logo images
//        card.setCardBackgroundColor(drawing); // set logo images
        if(!listItems[i].isLocked()){
            lock.setVisibility(View.INVISIBLE);
        }

        if(hearts>4 && listItems[i].isLocked()){
            if(i==0 || (!listItems[i-1].isLocked())){
                lock.setVisibility(View.INVISIBLE);
                download.setVisibility(View.VISIBLE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showConfirmDialog(i, download);
//                        listItems[i].setLocked(false);
//                        download.setVisibility(View.INVISIBLE);
//                        saveAchievementsList();
//                        setupHeartsBar();
                    }
                });
            }
        }

        return view;
    }

    public void saveAchievementsList(){
        SharedPreferences sharedPrefs = context.getSharedPreferences("Games", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        List<AchievementListItem> list = Arrays.asList(listItems);
        String json = gson.toJson(list);

        editor.putString("AchievementsList", json);
        editor.commit();
    }


    public void setupHeartsBar(){
        preferencesManagement.clearHearts();
        int[] coinsIV = {R.id.bar_heart1,R.id.bar_heart2,R.id.bar_heart3,R.id.bar_heart4,R.id.bar_heart5};
        for(int i=0; i<hearts;i++){
            ((ImageView)((Activity)context).findViewById(coinsIV[i])).setBackground(context.getResources().getDrawable(R.drawable.heart_2));
            if(i==4) i=hearts;
        }
    }

    private void showConfirmDialog(final int i, final ImageView download) {
        ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from((Activity)context).inflate(R.layout.dialog_confirm_unlock, viewGroup, false);
        TextView question = dialogView.findViewById(R.id.confirm_unlock);
        question.setText(R.string.question_unlock_drawing);
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)context);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button btn = dialogView.findViewById(R.id.button_no);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        Button btn2 = dialogView.findViewById(R.id.button_yes);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems[i].setLocked(false);
                download.setVisibility(View.INVISIBLE);
                saveAchievementsList();
                setupHeartsBar();
                alertDialog.cancel();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setLayout(100, 100);
        alertDialog.show();
    }
}