package com.android.gramatematyczna;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BackgroundSoundService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(0.05f, 0.05f);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
//        Toast.makeText(getApplicationContext(), "Playing Bohemian Rashpody in the Background",    Toast.LENGTH_SHORT).show();
        return startId;
    }

    public void onStart(Intent intent, int startId) {
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onLowMemory() {
    }
}
