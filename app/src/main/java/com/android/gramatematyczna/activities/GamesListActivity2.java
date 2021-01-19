package com.android.gramatematyczna.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.VoicePlayerService;
import com.android.gramatematyczna.adapters.GamesGridAdapter2;
import com.android.gramatematyczna.database.DatabaseHelper;
import com.android.gramatematyczna.games.GameListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class GamesListActivity2 extends AppCompatActivity {

    int coins = 0;
    int maxCoins = 8;
    PreferencesManagement preferencesManagement;
    int gamesList[] = {R.color.yellow, R.color.blue_light, R.color.pink_light, R.color.green_bright, R.color.purple, R.color.blue_strong, R.color.red_fire, R.color.purple_pope, R.color.green_neon, R.color.pink_powder, R.color.color_numbers, R.color.orange, R.color.yellow_neon};
//    GameListItem gamesListTMP[];

    boolean canUnlock = false;
    ArrayList<GameListItem> gameList;
    VoicePlayerService playerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.activity_games_list);

        preferencesManagement = new PreferencesManagement(GamesListActivity2.this);
        preferencesManagement.manage();
        //little cheat for test:
//        preferencesManagement.addCoins(8);

        DatabaseHelper helper = new DatabaseHelper(this);
        gameList = helper.getGames();
        System.out.println("GamesListActivity2.onCreate() games: " + gameList.size());

//        setupCoinsBar();
//        setupGridView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencesManagement = new PreferencesManagement(GamesListActivity2.this);
        preferencesManagement.manage();
        //little cheat for test:
//        preferencesManagement.addCoins(8);
        playerService = null;

        DatabaseHelper helper = new DatabaseHelper(this);
        gameList = helper.getGames();
        System.out.println("GamesListActivity2.onResume() games: " + gameList.size());

        setupCoinsBar();
        setupGridView();
    }

    public void setupGridView() {
        System.out.println("GamesListActivity2.setupGridView()");
//        GamesGridAdapter adapter = new GamesGridAdapter(GamesListActivity.this, gamesList);
        GamesGridAdapter2 adapter = new GamesGridAdapter2(GamesListActivity2.this, gameList, canUnlock);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
    }

    public void setFullScreenMode() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }

    int[] coinsIV = {R.id.bar_coin1, R.id.bar_coin2, R.id.bar_coin3, R.id.bar_coin4, R.id.bar_coin5, R.id.bar_coin6, R.id.bar_coin7, R.id.bar_coin8};

    public void setupCoinsBar() {
        coins = preferencesManagement.getCoins();
        for (int i = 0; i < maxCoins; i++) {
            if (i < coins)
                ((ImageView) findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.coin));
            else
                ((ImageView) findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.coin_2));
        }
        if (coins == 8) {
            Toast.makeText(this, "Możesz odblokowac gre!", Toast.LENGTH_SHORT).show();
            canUnlock = true;
            playSound(true);
        } else {
            playChooseGame();
        }
    }

    public GameListItem[] getGameList() {
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("GameList", "");
        List<GameListItem> arrayList = gson.fromJson(json, new TypeToken<List<GameListItem>>() {
        }.getType());
        GameListItem gamesListTMP2[] = arrayList.toArray(new GameListItem[0]);
        return gamesListTMP2;
    }

    private void setFirstUse() {
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("FirstUse", false);
        editor.commit();
    }

    private boolean getFirstUse() {
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        return sharedPrefs.getBoolean("FirstUse", true);
    }

    public void stopPlayer() {
        if (playerService != null) playerService.stop();
    }

    public void playSound(boolean canUnlock) {
        stopPlayer();
        if (preferencesManagement.playSounds()) {
            playerService = new VoicePlayerService(this);
            playerService.playUnlockGame(canUnlock);
        }
    }

    public void playAreYouSure() {
        stopPlayer();
        if (preferencesManagement.playSounds()) {
            playerService = new VoicePlayerService(this);
            playerService.playYouSureToUnlock();
        }
    }

    public void playChooseGame() {
        stopPlayer();
        if (preferencesManagement.playSounds()) {
            playerService = new VoicePlayerService(this);
            playerService.playChooseGame();
        }
    }

    public void unlockedGame() {
        preferencesManagement = new PreferencesManagement(this);
        preferencesManagement.clearCoins();
        setupCoinsBar();
        setupGridView();
    }
}
