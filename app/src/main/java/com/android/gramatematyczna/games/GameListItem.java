package com.android.gramatematyczna.games;

/**
 * Created by B.A.Wędrychowicz on 14.12.2020
 */
public class GameListItem {
    int color;
    int number;
    int gamePictureName;
    int gameType;
    //0-count elements, 1-memory game
    boolean gameLocked=true;

    public GameListItem(int color, int number, int gamePictureName) {
        this.color = color;
        this.number = number;
        this.gamePictureName = gamePictureName;
    }
    public GameListItem() {
    }

    public GameListItem(int color, int number, int gamePictureName, int gameType, boolean gameIsLocked) {
        this.color = color;
        this.number = number;
        this.gamePictureName = gamePictureName;
        this.gameType = gameType;
        this.gameLocked=gameIsLocked;
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
    }
    public boolean isGameLocked() {
        return gameLocked;
    }
    public void setGameLocked(boolean gameIsLocked) {
        this.gameLocked = gameIsLocked;
    }
}
