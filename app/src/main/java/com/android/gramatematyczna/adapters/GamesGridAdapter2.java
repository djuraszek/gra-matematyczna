package com.android.gramatematyczna.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.activities.AchievementsActivity;
import com.android.gramatematyczna.activities.GamesListActivity2;
import com.android.gramatematyczna.database.DatabaseHelper;
import com.android.gramatematyczna.games.GameCountActivity;
import com.android.gramatematyczna.games.GameListItem;
import com.android.gramatematyczna.games.GameMemoryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamesGridAdapter2 extends BaseAdapter {
    Context context;
    int colors[];
    LayoutInflater inflter;
    boolean isTMP = false;
    boolean canUnlock = false;
    int coins = 0;
    PreferencesManagement preferencesManagement;

    public GamesGridAdapter2(Context applicationContext, int[] colors) {
        this.context = applicationContext;
        this.colors = colors;
        inflter = (LayoutInflater.from(applicationContext));
    }

    ArrayList<GameListItem> gameList;

    public GamesGridAdapter2(Context applicationContext, ArrayList<GameListItem> gameList, boolean canUnlock) {
        this.context = applicationContext;
        this.gameList = gameList;
        inflter = (LayoutInflater.from(applicationContext));
        isTMP = true;
        preferencesManagement = new PreferencesManagement(context);
        preferencesManagement.manage();
        coins = preferencesManagement.getCoins();
        this.canUnlock = canUnlock;
        System.out.println("GamesGridAdapter2. canUnlock= "+canUnlock);
    }

    @Override
    public int getCount() {
        if (isTMP)
            return gameList.size();
        else return colors.length;
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
        GameListItem game = gameList.get(i);

        CardView card = (CardView) view.findViewById(R.id.cardview); // get the reference of ImageView
        int color;
        if (isTMP) {
            color = ResourcesCompat.getColor(context.getResources(), gameList.get(i).getColor(), null);
        } else {
            color = ResourcesCompat.getColor(context.getResources(), colors[i], null);
        }

        ImageView background = (ImageView) view.findViewById(R.id.card_background);
        ImageView lock = (ImageView) view.findViewById(R.id.item_lock);
        final ImageView download = (ImageView) view.findViewById(R.id.item_unlock);
        ImageView gameIcon = view.findViewById(R.id.game_icon);
        background.setBackgroundColor(color); // set logo images

//        card.setCardBackgroundColor(drawing); // set logo images
        if (gameList.get(i).getGameType() == 0) {
            String gameIconName = "img_number_" + gameList.get(i).getNumber();
            gameIcon.setImageResource(getDrawableByName(gameIconName));
        } else if (gameList.get(i).getGameType() == 1) {
            String gameIconName = "img_memory" + gameList.get(i).getNumber();
            gameIcon.setImageResource(getDrawableByName(gameIconName));
        }

        boolean unlocked = game.isUnlocked();
        lock.setVisibility(View.VISIBLE);

        if (unlocked) {
            lock.setVisibility(View.INVISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gameList.get(i).getGameType() == 0) {
                        Intent intent = new Intent(context, GameCountActivity.class);
                        intent.putExtra("NEW_NUMBER", gameList.get(i).getNumber());
                        context.startActivity(intent);
                        ((GamesListActivity2)context).stopPlayer();
                    } else if (gameList.get(i).getGameType() == 1) {
                        Intent intent = new Intent(context, GameMemoryActivity.class);
                        intent.putExtra("NEW_NUMBER", gameList.get(i).getNumber());
                        context.startActivity(intent);
                        ((GamesListActivity2)context).stopPlayer();
                    }
                }
            });

        } else if(coins > 7 && !unlocked && canUnlockGame(i)) {
            lock.setVisibility(View.INVISIBLE);
            download.setVisibility(View.VISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmDialog(i, download);
                }
            });
        }else {
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

    private int getDrawableByName(String name) {
        int resID = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return resID;
    }

    private void showConfirmDialog(final int i, final ImageView download) {
        playAreYouSure();
        ViewGroup viewGroup = ((Activity) context).findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from((Activity) context).inflate(R.layout.dialog_confirm_unlock, viewGroup, false);
        TextView question = dialogView.findViewById(R.id.confirm_unlock);
        question.setText(R.string.question_unlock_game);
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity) context);
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
                gameList.get(i).setLocked(false);
                download.setVisibility(View.INVISIBLE);
                alertDialog.cancel();
                setGameLocked(i);
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setLayout(100, 100);
        alertDialog.show();
    }

    public void playAreYouSure(){
        ((GamesListActivity2)context).playAreYouSure();

    }

    public void playCantUnlockSound(){
        ((GamesListActivity2)context).playSound(false);
    }

    public void updatedGrid(){
//        System.out.println("DrawingsGridAdapter: updateGrid()");
        ((GamesListActivity2)context).unlockedGame();
    }

    public void setGameLocked(int index) {
        gameList.get(index).setUnlocked(true);
        //zmien serduszka
        PreferencesManagement preferences = new PreferencesManagement(context);
        preferences.clearCoins();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        dbHelper.updateGame(gameList.get(index).getId());

        notifyDataSetChanged();
        updatedGrid();
    }

    public boolean canUnlockGame(int id){
        boolean canUnlock = false;
        //mozemy odblokowac, jesli wczesniejszy lvl jest odblokowany
        //dwie pierwsze sa odblokowane
        if(id >= 2){
            if(gameList.get(id-2).isUnlocked() || gameList.get(id-1).isUnlocked())
                return true;
        }
        return canUnlock;
    }
}