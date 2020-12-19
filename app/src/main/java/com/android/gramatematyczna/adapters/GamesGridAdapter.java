package com.android.gramatematyczna.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
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
import androidx.core.content.res.ResourcesCompat;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.activities.GameActivity;
import com.android.gramatematyczna.activities.GamesListActivity;
import com.android.gramatematyczna.games.GameCountActivity;
import com.android.gramatematyczna.games.GameListItem;
import com.android.gramatematyczna.games.GameMemoryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class GamesGridAdapter extends BaseAdapter {
    Context context;
    int colors[];
    LayoutInflater inflter;
    boolean isTMP=false;

    int coins = 0;
    PreferencesManagement preferencesManagement;

    public GamesGridAdapter(Context applicationContext, int[] colors) {
        this.context = applicationContext;
        this.colors = colors;
        inflter = (LayoutInflater.from(applicationContext));
    }

    GameListItem gamesListTMP[];
    public GamesGridAdapter(Context applicationContext, GameListItem[] gamesListTMP) {
        this.context = applicationContext;
        this.gamesListTMP = gamesListTMP;
        inflter = (LayoutInflater.from(applicationContext));
        isTMP=true;
        preferencesManagement = new PreferencesManagement(context);
        preferencesManagement.manage();
        coins = preferencesManagement.getCoins();
    }

    @Override
    public int getCount() {
         if(isTMP)
             return gamesListTMP.length;
         else return  colors.length;
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
        view = inflter.inflate(R.layout.item_grid_drawings, null); // inflate the layout
        CardView card = (CardView) view.findViewById(R.id.cardview); // get the reference of ImageView
        int color;
        if(isTMP){
            color = ResourcesCompat.getColor(context.getResources(), gamesListTMP[i].getColor(), null);
        }else{
            color = ResourcesCompat.getColor(context.getResources(), colors[i], null);
        }

        ImageView background = (ImageView)view.findViewById(R.id.card_background);
        ImageView lock = (ImageView)view.findViewById(R.id.item_lock);
        final ImageView download = (ImageView)view.findViewById(R.id.item_unlock);
        ImageView gameIcon = view.findViewById(R.id.game_icon);
        background.setBackgroundColor(color); // set logo images
//        card.setCardBackgroundColor(drawing); // set logo images
        if(gamesListTMP[i].getGameType()==0) {
            String gameIconName="img_number_"+gamesListTMP[i].getNumber();
            gameIcon.setImageResource(getDrawableByName(gameIconName));
        }else if(gamesListTMP[i].getGameType()==1) {
            String gameIconName="img_memory"+gamesListTMP[i].getNumber();
            gameIcon.setImageResource(getDrawableByName(gameIconName));
        }
        if(!gamesListTMP[i].isLocked()) {
            lock.setVisibility(View.INVISIBLE);
            if(isTMP) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(gamesListTMP[i].getGameType()==0) {
                            Intent intent = new Intent(context, GameCountActivity.class);
                            intent.putExtra("NEW_NUMBER", gamesListTMP[i].getNumber());
                            context.startActivity(intent);
                        }else if(gamesListTMP[i].getGameType()==1){
                            Intent intent = new Intent(context, GameMemoryActivity.class);
                            intent.putExtra("NEW_NUMBER", gamesListTMP[i].getNumber());
                            context.startActivity(intent);
                        }
                    }
                });
            }else{
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GameCountActivity.class);
                        intent.putExtra("NEW_NUMBER", 7);
                        context.startActivity(intent);
                    }
                });
            }
        }
            //TODO choose game to unlock
            if(coins>7 && gamesListTMP[i].isLocked() && !gamesListTMP[i-1].isLocked()){
                lock.setVisibility(View.INVISIBLE);
                download.setVisibility(View.VISIBLE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showConfirmDialog(i, download);
//                        gamesListTMP[i].setLocked(false);
//                        download.setVisibility(View.INVISIBLE);
//                        saveGameList();
//                        setupCoinsBar();
                    }
                });
            }
        return view;
    }

    private int getDrawableByName(String name){
        int resID = context.getResources().getIdentifier(name , "drawable", context.getPackageName());
        return resID;
    }

    public void saveGameList(){
        SharedPreferences sharedPrefs = context.getSharedPreferences("Games", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        List <GameListItem> list = Arrays.asList(gamesListTMP);
        String json = gson.toJson(list);

        editor.putString("GameList", json);
        editor.commit();
    }
    public GameListItem[] getGameList(){
        SharedPreferences sharedPrefs = context.getSharedPreferences("Games", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("GameList", "");
        List<GameListItem> arrayList = gson.fromJson(json, new TypeToken<List<GameListItem>>() {}.getType());
        GameListItem gamesListTMP2[]=arrayList.toArray(new GameListItem[0]);
        return gamesListTMP2;
    }

    public void setupCoinsBar(){
    preferencesManagement.clearCoins();
    int[] coinsIV = {R.id.bar_coin1,R.id.bar_coin2,R.id.bar_coin3,R.id.bar_coin4,R.id.bar_coin5,R.id.bar_coin6,R.id.bar_coin7,R.id.bar_coin8};
        for(int i=0; i<coins;i++){
            ((ImageView)((Activity)context).findViewById(coinsIV[i])).setBackground(context.getResources().getDrawable(R.drawable.coin_2));
            if(i==7)i=coins;
        }
    }

    private void showConfirmDialog(final int i, final ImageView download) {
        ViewGroup viewGroup = ((Activity)context).findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from((Activity)context).inflate(R.layout.dialog_confirm_unlock, viewGroup, false);
        TextView question = dialogView.findViewById(R.id.confirm_unlock);
        question.setText(R.string.question_unlock_game);
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
                gamesListTMP[i].setLocked(false);
                download.setVisibility(View.INVISIBLE);
                saveGameList();
                setupCoinsBar();
                alertDialog.cancel();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setLayout(100, 100);
        alertDialog.show();
    }

}