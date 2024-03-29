package com.android.gramatematyczna.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.gramatematyczna.VoicePlayerService;
import com.android.gramatematyczna.adapters.DrawingsGridAdapter;
import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.classes.Drawing;
import com.android.gramatematyczna.database.DatabaseHelper;

import java.util.ArrayList;

public class AchievementsActivity extends AppCompatActivity {

    int[] drawingList = {R.drawable.rys_1, R.drawable.rys_2, R.drawable.rys_3, R.drawable.rys_4, R.drawable.rys_5, R.drawable.rys_6, R.drawable.rys_7, R.drawable.rys_8, R.drawable.rys_9, R.drawable.rys_10};
    int hearts = 0;
    boolean canUnlock = false;
    boolean playVoice = false;
    PreferencesManagement preferencesManagement;
    VoicePlayerService playerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_achievements);
        preferencesManagement = new PreferencesManagement(AchievementsActivity.this);
        preferencesManagement.manage();


        getHearts();
        setupGridView();
        setupHeartsBar();
    }

    public void getHearts(){
        hearts = preferencesManagement.getHearts();
        Log.d("AchievementsActivity","getHearts(): "+hearts);
//        hearts = 5;
        if (hearts >= 5) {
            Toast.makeText(this, "Możesz odblokowac kolorowanke!", Toast.LENGTH_SHORT).show();
            canUnlock = true;
            playSound(true);
        }else{
            canUnlock = false;
        }
    }

    public void setupGridView(){
        Log.d("AchievementsActivity","setupGridView()");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.clearData();
        ArrayList<Drawing> drawings = dbHelper.getDrawings();

        Log.e("AchievementsActivity", "drawings: " + drawings.size());
        DrawingsGridAdapter adapter = new DrawingsGridAdapter(AchievementsActivity.this, drawings, canUnlock) {
//            @Override
//            public void updatedGrid() {
//                Log.d("DrawingsGridAdapter","updateGrid()");
////                super.updatedGrid();
////                setupHeartsBar();
////                setupGridView();
//            }
        };
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
    }

    public void unlockedPicture(){
        preferencesManagement = new PreferencesManagement(this);
        preferencesManagement.clearHearts();
        getHearts();
        setupHeartsBar();
        setupGridView();

        playUnlockedPicture();
    }



    public void onBackPressed(View view) {
        super.onBackPressed();
        stopPlayer();
        finish();
    }

    int[] coinsIV = {R.id.bar_heart1, R.id.bar_heart2, R.id.bar_heart3, R.id.bar_heart4, R.id.bar_heart5};

    public void setupHeartsBar() {
        Log.e("AchievementsActivity","setupHeartsBar(): "+hearts);
        for (int i = 0; i < coinsIV.length; i++) {
            if(i<hearts) {
                ((ImageView) findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.heart));
//                if (i == 4) i = hearts;
            }else{
                ((ImageView) findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.heart_2));
            }
        }
    }

    public void stopPlayer(){
        if(playerService!=null) playerService.stop();
    }

    public void playSound(boolean canUnlock){
        stopPlayer();
        if(preferencesManagement.playSounds()) {
             playerService = new VoicePlayerService(this);
            playerService.playUnlockAchievements(canUnlock);
        }
    }

    public void playUnlockedPicture(){
        stopPlayer();
        if(preferencesManagement.playSounds()) {
            playerService = new VoicePlayerService(this);
            playerService.playUnlockedAchievement();
        }
    }
}
