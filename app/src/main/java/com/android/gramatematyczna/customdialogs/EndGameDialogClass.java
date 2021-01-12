package com.android.gramatematyczna.customdialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.VoicePlayerService;
import com.android.gramatematyczna.activities.GamesListActivity;
import com.android.gramatematyczna.games.GameCountActivity;

import java.util.List;

public class EndGameDialogClass extends Dialog implements View.OnClickListener {
    public Activity activity;
    Button homeBtn, resumeBtn, listBtn;
    ImageView starsIV, coin1IV, coin2IV, coin3IV, heartIV;
    int goodAnswers = 0;
    int starsNumber = 0;


    public EndGameDialogClass(Activity activity, int goodAnswers) {
        super(activity);
        this.activity = activity;
        this.goodAnswers = goodAnswers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_end_game);
        listBtn = (Button) findViewById(R.id.list_btn);
        listBtn.setOnClickListener(this);
        resumeBtn = (Button) findViewById(R.id.resume_btn);
        resumeBtn.setOnClickListener(this);

        starsIV = (ImageView) findViewById(R.id.stars_imageview);
        coin1IV = (ImageView) findViewById(R.id.coin_iv);
        coin2IV = (ImageView) findViewById(R.id.coin2_iv);
        coin3IV = (ImageView) findViewById(R.id.coin3_iv);
        heartIV = (ImageView) findViewById(R.id.heart_iv);

        setGraphics();
        if (preferencesManagement != null)
            playSound();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void setGraphics() {
        Resources res = activity.getResources();
        if (goodAnswers < 3) {
            starsNumber = 1;
            starsIV.setBackground(res.getDrawable(R.drawable.stars_1));
            coin1IV.setBackground(res.getDrawable(R.drawable.coin));
        } else if (goodAnswers < 5) {
            starsNumber = 2;
            starsIV.setBackground(res.getDrawable(R.drawable.stars_2));
            coin1IV.setBackground(res.getDrawable(R.drawable.coin));
            coin2IV.setBackground(res.getDrawable(R.drawable.coin));
        } else if (goodAnswers == 5) {
            starsNumber = 3;
            starsIV.setBackground(res.getDrawable(R.drawable.stars_3));
            coin1IV.setBackground(res.getDrawable(R.drawable.coin));
            coin2IV.setBackground(res.getDrawable(R.drawable.coin));
            coin3IV.setBackground(res.getDrawable(R.drawable.coin));
            heartIV.setBackground(res.getDrawable(R.drawable.heart));
        } else {
            starsNumber = 0;
            starsIV.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_btn:
                goToListScreen();
                break;
            case R.id.resume_btn:
                playAgain();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void playAgain() {

    }

    public void goToListScreen() {
        Intent intent = new Intent(activity, GamesListActivity.class);
        activity.startActivity(intent);
        dismiss();
        activity.finish();
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void setupPrefManagement(PreferencesManagement preferencesManagement) {
        this.preferencesManagement = preferencesManagement;
    }

    VoicePlayerService playerService;
    PreferencesManagement preferencesManagement;

    public void stopPlayer() {
        if (playerService != null) playerService.stop();
    }

    public void playSound() {
        stopPlayer();

        if (preferencesManagement.playSounds()) {
            playerService = new VoicePlayerService(activity);
            playerService.playEndGame(starsNumber);
        }
    }

}
