package com.android.gramatematyczna.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.VoicePlayerService;
import com.android.gramatematyczna.activities.GamesListActivity;
import com.android.gramatematyczna.activities.MainActivity;

import java.util.List;

public class PauseDialogClass extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    Button homeBtn, resumeBtn, listBtn;

    public PauseDialogClass(Activity activity) {
        super(activity);
        this.activity = activity;
        preferencesManagement = new PreferencesManagement(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_pause);
        homeBtn = (Button) findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(this);
        listBtn = (Button) findViewById(R.id.list_btn);
        listBtn.setOnClickListener(this);
        resumeBtn = (Button) findViewById(R.id.resume_btn);
        resumeBtn.setOnClickListener(this);
        playSound();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_btn:
                stopPlayer();
                goToHomeScreen();
                break;
            case R.id.list_btn:
                stopPlayer();
                goToListScreen();
                break;
            case R.id.resume_btn:
                stopPlayer();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void goToHomeScreen() {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        this.dismiss();
        activity.finish();
    }

    public void goToListScreen() {
//        Intent intent = new Intent(activity, GamesListActivity.class);
//        activity.startActivity(intent);
        this.dismiss();
        activity.finish();
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void setupPrefManagement(PreferencesManagement preferencesManagement) {
//        this.preferencesManagement = preferencesManagement;
//        playSound();
    }

    VoicePlayerService playerService;
    PreferencesManagement preferencesManagement;

    public void stopPlayer() {
        if (playerService != null) playerService.stop();
    }

    public void playSound() {
        stopPlayer();
        boolean play = preferencesManagement.playSounds();
        System.out.println("21 playSound "+play);
        if (play) {
            playerService = new VoicePlayerService(activity);
            playerService.playPauseGame();
        }
    }
}
