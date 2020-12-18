package com.android.gramatematyczna;

/**
 * Created by B.A.Wędrychowicz on 18.12.2020
 */
public class AchievementListItem {
    int picName;
    boolean isLocked;

    public AchievementListItem(int picName) {
        this.picName = picName;
        this.isLocked = true;
    }

    public int getPicName() {
        return picName;
    }
    public void setPicName(int picName) {
        this.picName = picName;
    }
    public boolean isLocked() {
        return isLocked;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
