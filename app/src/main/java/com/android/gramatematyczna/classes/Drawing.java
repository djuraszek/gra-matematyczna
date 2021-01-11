package com.android.gramatematyczna.classes;

import java.util.Arrays;

public class Drawing {
    int id;
    String code;
    boolean unlocked = false;
    byte[] img;
    //0-count elements, 1-memory game

    public Drawing() {
    }

    public Drawing(int id, String code, boolean unlocked) {
        this.id = id;
        this.code = code;
        this.unlocked = unlocked;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
        return "Drawing{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", unlocked=" + unlocked +
                '}';
    }
}
