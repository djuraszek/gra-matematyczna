package com.android.gramatematyczna.games;

import java.util.Arrays;

/**
 * Created by B.A.Wędrychowicz on 14.12.2020
 */
public class GameListItem {
    int id;
    int color;
    int number;
    int gamePictureName;
    int gameType;
    boolean unlocked = false;
    byte[] img;
    //0-count elements, 1-memory game
    boolean isLocked;

    public GameListItem(int color, int number, int gamePictureName, int gameType, boolean isLocked) {
        this.color = color;
        this.number = number;
        this.gamePictureName = gamePictureName;
        this.gameType = gameType;
        this.isLocked = isLocked;
    }

    public GameListItem() {
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getGamePictureName() {
        return gamePictureName;
    }
    public void setGamePictureName(int gamePictureName) {
        this.gamePictureName = gamePictureName;
    }
    public int getGameType() {
        return gameType;
    }
    public void setGameType(int gameType) {
        this.gameType = gameType;
        this.unlocked = unlocked;
    }

    public GameListItem(int id,int number, int gameType,boolean unlocked) {
        this.id = id;
        this.number = number;
        this.gamePictureName = gamePictureName;
        this.gameType = gameType;
        this.unlocked = unlocked;
    }
    public boolean isLocked() {
        return isLocked;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "GameListItem{" +
                "id=" + id +
                ", number=" + number +
                ", gamePictureName=" + gamePictureName +
                ", gameType=" + gameType +
                ", unlocked=" + unlocked +
                '}';
    }
}
