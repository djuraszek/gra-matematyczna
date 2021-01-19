package com.android.gramatematyczna;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class VoicePlayerService {

    MediaPlayer mediaPlayer;
    int rawFile;
    Context c;

    public VoicePlayerService(Context c) {
        this.c = c;
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void playUnlockAchievements(boolean canUnlock) {
        if (canUnlock)
            rawFile = R.raw.wybierz_rys_do_odblok;
        else
            rawFile = R.raw.aby_odblokowac_rys;
        play();
    }

    public void playUnlockedAchievement(){
        rawFile = R.raw.odblokowano_rys;
        play();
    }

    public void playChooseGame() {
        rawFile = R.raw.wybierz_gre;
        play();
    }

    public void playUnlockGame(boolean canUnlock) {
        if (canUnlock)
            rawFile = R.raw.odblokuj_gre;
        else
            rawFile = R.raw.aby_odblokowac_gre;
        play();
    }

    public void playYouSureToUnlock(){
        rawFile = R.raw.czy_na_pewno_odb;
        play();
    }

    public void playPauseGame() {
        rawFile = R.raw.przerwa;
        play();
    }

    public void playAnswerVoice(boolean good) {
        if (good) rawFile = R.raw.bardzo_dobrze;
        else rawFile = R.raw.zla_odp;
        play();
    }

    public void readGameCommand(int game) {
        if (game == 1) {
            rawFile = R.raw.gra_1;
        } else if (game == 2) {
            rawFile = R.raw.gra_2;
        } else {
            return;
        }
        play();
    }

    public void playEndGame(int stars) {
        if (stars == 0) rawFile = R.raw.gwiazdek_0;
        else if (stars == 1) rawFile = R.raw.gwiazdek_1;
        else if (stars == 2) rawFile = R.raw.gwiazdek_2;
        else if (stars == 3) rawFile = R.raw.gwiazdek_3;
        play();
    }


    public void play() {
        mediaPlayer = MediaPlayer.create(c, rawFile);
        mediaPlayer.setLooping(false); // Set looping
        mediaPlayer.setVolume(0.08f, 0.08f);
        mediaPlayer.start();
    }
}
