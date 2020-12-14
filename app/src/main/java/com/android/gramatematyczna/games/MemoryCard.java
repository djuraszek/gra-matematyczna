package com.android.gramatematyczna.games;

/**
 * Created by B.A.Wędrychowicz on 13.12.2020
 */
class MemoryCard {
    int number;
    String pictureName;
    int pictureID;
    int pictureBack;
    boolean isOpen=false;
    boolean isOpenCorrect=false;
    int position;

    public MemoryCard(int number, String pictureName, int pictureID, int pictureBack) {
        this.number = number;
        this.pictureName = pictureName;
        this.pictureID = pictureID;
        this.pictureBack=pictureBack;
    }
    public MemoryCard() {}

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getPictureName() {
        return pictureName;
    }
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
    public int getPictureID() {
        return pictureID;
    }
    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }
    public boolean isOpenCorrect() {
        return isOpenCorrect;
    }
    public void setOpenCorrect(boolean openCorrect) {
        isOpenCorrect = openCorrect;
    }
    public int getPictureBack() {
        return pictureBack;
    }
    public void setPictureBack(int pictureBack) {
        this.pictureBack = pictureBack;
    }

    public void close(){
        isOpen=false;
    }
    public void open(){
        isOpen=true;
    }
    public void leftOpen(){
        isOpenCorrect=true;
    }
}
