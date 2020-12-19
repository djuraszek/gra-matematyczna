package com.android.gramatematyczna.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.adapters.GamesGridAdapter;
import com.android.gramatematyczna.games.GameListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GamesListActivity extends AppCompatActivity {

    int coins = 0;
    PreferencesManagement preferencesManagement;
    int gamesList[] = {R.color.yellow, R.color.blue_light, R.color.pink_light, R.color.green_bright, R.color.purple, R.color.blue_strong, R.color.red_fire, R.color.purple_pope, R.color.green_neon, R.color.pink_powder, R.color.color_numbers, R.color.orange, R.color.yellow_neon};
    GameListItem gamesListTMP[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.activity_games_list);

        preferencesManagement = new PreferencesManagement(GamesListActivity.this);
        preferencesManagement.manage();
        //little cheat for test:
//        preferencesManagement.addCoins(8);
        coins = preferencesManagement.getCoins();

        if(getFirstUse()){
            GameListItem gamesListTMP2[] = {new GameListItem(R.color.yellow, 3, 0,0, false),
                    new GameListItem(R.color.green_neon, 4, 0,0, false),
                    new GameListItem(R.color.blue_light, 5, 0,0, false),
                    new GameListItem(R.color.blue_light, 5,  0,1, true),
                    new GameListItem(R.color.purple, 6, 0,0, true),
                    new GameListItem(R.color.purple, 6,  0,1, true),
                    new GameListItem(R.color.red_fire, 7, 0,0, true),
                    new GameListItem(R.color.red_fire, 7,  0,1, true),
                    new GameListItem(R.color.pink_powder, 8, 0,0, true),
                    new GameListItem(R.color.pink_powder, 8,  0,1, true),
                    new GameListItem(R.color.yellow, 9, 0,0, true),
                    new GameListItem(R.color.yellow, 9,  0,1, true),
                    new GameListItem(R.color.green_neon, 10, 0,0, true),
                    new GameListItem(R.color.green_neon, 10,  0,1, true)};
            gamesListTMP=gamesListTMP2;
            setFirstUse();
        }else{
            gamesListTMP=getGameList();
        }


//        GamesGridAdapter adapter = new GamesGridAdapter(GamesListActivity.this, gamesList);
        GamesGridAdapter adapter = new GamesGridAdapter(GamesListActivity.this, gamesListTMP);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        setupCoinsBar();
    }

    public void setFullScreenMode(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }


    int[] coinsIV = {R.id.bar_coin1,R.id.bar_coin2,R.id.bar_coin3,R.id.bar_coin4,R.id.bar_coin5,R.id.bar_coin6,R.id.bar_coin7,R.id.bar_coin8};
    public void setupCoinsBar(){
        for(int i=0; i<coins;i++){
            ((ImageView)findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.coin));
            if(i==7)i=coins;
        }
        if(coins==8){
            Toast.makeText(this,"Możesz odblokowac gre!",Toast.LENGTH_SHORT).show();
        }
    }

    public GameListItem[] getGameList(){
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("GameList", "");
        List<GameListItem> arrayList = gson.fromJson(json, new TypeToken<List<GameListItem>>() {}.getType());
        GameListItem gamesListTMP2[]=arrayList.toArray(new GameListItem[0]);
        return gamesListTMP2;
    }
    private void setFirstUse(){
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("FirstUse", false);
        editor.commit();
    }
    private boolean getFirstUse(){
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        return sharedPrefs.getBoolean("FirstUse", true);
    }


}
