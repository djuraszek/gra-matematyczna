package com.android.gramatematyczna.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.adapters.GamesGridAdapter;
import com.android.gramatematyczna.games.GameListItem;

public class GamesListActivity extends AppCompatActivity {

    PreferencesManagement preferencesManagement;
    int gamesList[] = {R.color.yellow, R.color.blue_light, R.color.pink_light, R.color.green_bright, R.color.purple, R.color.blue_strong, R.color.red_fire, R.color.purple_pope, R.color.green_neon, R.color.pink_powder, R.color.color_numbers, R.color.orange, R.color.yellow_neon};
    GameListItem gamesListTMP[] = {new GameListItem(R.color.yellow, 7, 0,0), new GameListItem(R.color.green_neon, 6,  0,1), new GameListItem(R.color.blue_strong, 3,  0, 0)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.activity_games_list);

        preferencesManagement = new PreferencesManagement(GamesListActivity.this);
        preferencesManagement.manage();

//        GamesGridAdapter adapter = new GamesGridAdapter(GamesListActivity.this, gamesList);
        GamesGridAdapter adapter = new GamesGridAdapter(GamesListActivity.this, gamesListTMP);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
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

    private int getDrawableByName(String name){
        int resID = getResources().getIdentifier(name , "drawable", getPackageName());
        return resID;
    }
}
