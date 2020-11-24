package com.android.gramatematyczna.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.android.gramatematyczna.BackgroundSoundService;
import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;

public class MainActivity extends AppCompatActivity {

    Button startBtn, achievementsBtn, exitBtn;
    Button soundBtn, musicBtn;
    Animation animShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);

        PreferencesManagement preferencesManagement = new PreferencesManagement(MainActivity.this);
        preferencesManagement.manage();

        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        System.out.println("MainActivity.onCreate()");
    }

    public void buttonClick(View view) {
        switch(view.getId()) {
            case R.id.start_btn:
                goToGamesList(view);
                return;
            case R.id.achievements_btn:
                showAchievements(view);
                return;
            case R.id.exit_btn:
                finish();
                System.exit(0);
                return;
            default:
                return;
        }
    }

    public void goToGamesList(View view){
        animShake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent = new Intent(MainActivity.this, GamesListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animShake);
    }

    public void showAchievements(View view){
        animShake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent = new Intent(MainActivity.this, AchievementsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animShake);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PreferencesManagement preferencesManagement = new PreferencesManagement(MainActivity.this);
        preferencesManagement.manage();
        System.out.println("MainActivity.onRestar()");
    }

    private void playBackgroundMusic(){
        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intent);
    }
}
