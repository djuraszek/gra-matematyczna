package com.android.gramatematyczna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesManagement {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    Button musicBtn, soundBtn;
    TextView moneyTV;

    int coins = 0, hearts = 0;

    int maxCoins = 8;
    int maxHearts = 5;

    BackgroundSoundService soundService;
    VoicePlayerService voicePlayerService;

    public PreferencesManagement(Context c) {
        context = c;
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);



        preferences = context.getSharedPreferences("Settings", MODE_PRIVATE);
        musicBtn = rootView.findViewById(R.id.music_btn);
        soundBtn = rootView.findViewById(R.id.sound_btn);
        moneyTV = rootView.findViewById(R.id.moneyTV);

        soundService = new BackgroundSoundService();

        setup();
    }

    public void setup() {
        coins = preferences.getInt("coins", 0);
        hearts = preferences.getInt("hearts", 0);

        boolean isMusicOn = preferences.getBoolean("isMusicOn", true);
        if (!isMusicOn) {
            musicBtn.setBackground(context.getDrawable(R.drawable.ic_music_off));
        } else {
            musicBtn.setBackground(context.getDrawable(R.drawable.ic_music_on));
            Intent intent = new Intent(context, BackgroundSoundService.class);
            context.startService(intent);
        }
        boolean isSoundOn = preferences.getBoolean("isSoundOn", true);
        if (!isSoundOn) soundBtn.setBackground(context.getDrawable(R.drawable.ic_sound_off));
        else soundBtn.setBackground(context.getDrawable(R.drawable.ic_sound_on));
    }

    public boolean playSounds(){
        return preferences.getBoolean("isSoundOn", true);
    }

    public void manage() {
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isMusicOn = preferences.getBoolean("isMusicOn", true);
                Log.d("PreferencesManagement", "Music change to on = " + !isMusicOn);
                edit = preferences.edit();
                edit.putBoolean("isMusicOn", !isMusicOn);
                edit.apply();
                if (isMusicOn) {
                    view.setBackground(context.getDrawable(R.drawable.ic_music_off));
                    Intent intent = new Intent(context, BackgroundSoundService.class);
                    context.stopService(intent);
                } else {
                    view.setBackground(context.getDrawable(R.drawable.ic_music_on));
                    Intent intent = new Intent(context, BackgroundSoundService.class);
                    context.startService(intent);
                }
            }
        });

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSoundOn = preferences.getBoolean("isSoundOn", true);
                Log.d("PreferencesManagement", "Sound change to on = " + !isSoundOn);
                edit = preferences.edit();
                edit.putBoolean("isSoundOn", !isSoundOn);
                edit.apply();
                if (isSoundOn) view.setBackground(context.getDrawable(R.drawable.ic_sound_off));
                else view.setBackground(context.getDrawable(R.drawable.ic_sound_on));
            }
        });
    }


    public void addCoins(int nr) {
        coins = preferences.getInt("coins", 0);
        edit = preferences.edit();

        coins += nr;
        if (coins > maxCoins) coins = maxCoins;
        edit.putInt("coins", coins);
        edit.apply();

        //jesli dostajemy wszystki pkt to serduszko tez
        if (nr == 3) {
            addHeart();
        }
    }

    public void setMaxHearts(){
        edit = preferences.edit();
        edit.putInt("hearts", maxHearts);
        edit.apply();
    }

    public void addHeart() {
        if (hearts <= maxHearts) {
            hearts = preferences.getInt("hearts", 0);
            edit = preferences.edit();

            hearts += 1;
            edit.putInt("hearts", hearts);
            edit.apply();
        }
    }
    public void setFullHeart() {
        hearts = preferences.getInt("hearts", 0);
        edit = preferences.edit();
        hearts =5;
        edit.putInt("hearts", hearts);
        edit.apply();
    }

    public int getCoins() {
        return coins;
    }

    public int getHearts() {
        return hearts;
    }


    public void clearHearts() {
        Log.d("PreferencesManagement","hearts cleared");
        edit = preferences.edit();
        edit.putInt("hearts", 0);
        edit.apply();
    }

    public void clearCoins() {
        edit = preferences.edit();
        edit.putInt("coins", 0);
        edit.apply();
    }
}
