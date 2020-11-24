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

    BackgroundSoundService soundService;

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
        if (moneyTV != null) {
            int money = preferences.getInt("money", 0);
            moneyTV.setText("" + money);
        }

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
                }
                else {
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


    public void addPoints(int points) {
        int money = preferences.getInt("money", 0);
        edit = preferences.edit();

        money += points;
        edit.putInt("money", money);
        edit.apply();

        moneyTV.setText("" + money);
    }

}
