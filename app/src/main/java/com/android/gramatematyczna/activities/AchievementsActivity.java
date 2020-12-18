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

import com.android.gramatematyczna.AchievementListItem;
import com.android.gramatematyczna.adapters.DrawingsGridAdapter;
import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.games.GameListItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    int[] drawingList = {R.drawable.rys_1,R.drawable.rys_2,R.drawable.rys_3,R.drawable.rys_4,R.drawable.rys_5,R.drawable.rys_6,R.drawable.rys_7,R.drawable.rys_8,R.drawable.rys_9,R.drawable.rys_10};
    int hearts = 0;

    AchievementListItem[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_achievements);
        PreferencesManagement preferencesManagement = new PreferencesManagement(AchievementsActivity.this);
        preferencesManagement.manage();
        //little cheat for test:
//        preferencesManagement.setFullHeart();
        hearts = preferencesManagement.getHearts();

        if(getFirstUse()){
            AchievementListItem[] listItems2 = {
                    new AchievementListItem(R.drawable.rys_1),
                    new AchievementListItem(R.drawable.rys_2),
                    new AchievementListItem(R.drawable.rys_3),
                    new AchievementListItem(R.drawable.rys_4),
                    new AchievementListItem(R.drawable.rys_5),
                    new AchievementListItem(R.drawable.rys_6),
                    new AchievementListItem(R.drawable.rys_7),
                    new AchievementListItem(R.drawable.rys_8),
                    new AchievementListItem(R.drawable.rys_9),
                    new AchievementListItem(R.drawable.rys_10),
            };
            listItems=listItems2;
            setFirstUse();
        }else{
            listItems=getAchievementsList();
        }


        DrawingsGridAdapter adapter = new DrawingsGridAdapter(AchievementsActivity.this, listItems);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        setupHeartsBar();
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }

    int[] coinsIV = {R.id.bar_heart1,R.id.bar_heart2,R.id.bar_heart3,R.id.bar_heart4,R.id.bar_heart5};
    public void setupHeartsBar(){
        for(int i=0; i<hearts;i++){
            ((ImageView)findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.heart));
            if(i==4) i=hearts;
        }
        if(hearts==5){
            Toast.makeText(this,"Możesz odblokowac kolorowanke!",Toast.LENGTH_SHORT).show();
        }
    }

    public AchievementListItem[] getAchievementsList(){
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("AchievementsList", "");
        List<AchievementListItem> arrayList = gson.fromJson(json, new TypeToken<List<AchievementListItem>>() {}.getType());
        AchievementListItem gamesListTMP2[]=arrayList.toArray(new AchievementListItem[0]);
        return gamesListTMP2;
    }
    private void setFirstUse(){
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("FirstUseAchievements", false);
        editor.commit();
    }
    private boolean getFirstUse(){
        SharedPreferences sharedPrefs = getSharedPreferences("Games", Activity.MODE_PRIVATE);
        return sharedPrefs.getBoolean("FirstUseAchievements", true);
    }

}
