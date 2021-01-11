package com.android.gramatematyczna.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.gramatematyczna.classes.Drawing;
import com.android.gramatematyczna.games.GameListItem;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game.db";
    private static final int DATABASE_VERSION = 1;
    ConstVals consts = new ConstVals();

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + consts.TABLE_DRAWINGS_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + consts.TABLE_GAMES_NAME);

//        db.execSQL(consts.SQL_CREATE_TABLE_DRAWINGS);
//        db.execSQL(consts.SQL_CREATE_TABLE_GAMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void add() {
        ContentValues values = new ContentValues();

        values.put(consts.COLUMN_LEVEL, 3);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(consts.TABLE_GAMES_NAME, null, values);
        db.close();
    }
//
//    public void addMeasure(Measures obj) {
//        Log.e("MyDBHandler", "addMeasures ");
//        ContentValues values = new ContentValues();
//
//        values.put(consts.MEASURE_COLUMN_DATE, obj.getDate());
//        values.put(consts.MEASURE_COLUMN_WAIST, obj.getWaist());
//        values.put(consts.MEASURE_COLUMN_BELLY, obj.getBelly());
//        values.put(consts.MEASURE_COLUMN_HIPS, obj.getHips());
//        values.put(consts.MEASURE_COLUMN_THIGH_R, obj.getThighR());
//        values.put(consts.MEASURE_COLUMN_THIGH_L, obj.getThighL());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(consts.TABLE_DRAWINGS_NAME, null, values);
//        db.close();
//    }


    //******************************************************************************************
    //                              G  E  T
    //******************************************************************************************

    public ArrayList<GameListItem> getGames() {
        ArrayList<GameListItem> games = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + consts.TABLE_GAMES_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all records and adding to the list
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(consts.COLUMN_ID));
                int number = c.getInt(c.getColumnIndex(consts.COLUMN_LEVEL));
                int type = c.getInt(c.getColumnIndex(consts.COLUMN_GAME_TYPE));
                int unLocked = c.getInt(c.getColumnIndex(consts.COLUMN_UNLOCKED));
                byte[] img = c.getBlob(c.getColumnIndex(consts.COLUMN_BLOB));

                boolean unlocked = false;
                if (unLocked == 1) unlocked = true;
                GameListItem r = new GameListItem(id, number, type, unlocked);
                r.setImg(img);

                //todo
//                r.addExcercises(getExcercisesList(name));
                games.add(r);
            } while (c.moveToNext());
        }
        db.close();
        return games;
    }

    public ArrayList<Drawing> getDrawings() {
        ArrayList<Drawing> drawings = new ArrayList<>();
//        String selectQuery = "SELECT  * FROM " + consts.TABLE_DRAWINGS_NAME;
        String selectQuery = "SELECT  * FROM Drawings";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all records and adding to the list
//        Log.i("DatabaseHelper.getDrawings", "rows " + c.getCount());
        if (c.moveToFirst()) {
            do {
//                Log.i("DatabaseHelper.getDrawings", "rows " + c.toString());
                int id = c.getInt(c.getColumnIndex(consts.COLUMN_ID));
                int unLocked = c.getInt(c.getColumnIndex(consts.COLUMN_UNLOCKED));
                String code = c.getString(c.getColumnIndex(consts.COLUMN_CODE));
                byte[] img = c.getBlob(c.getColumnIndex(consts.COLUMN_BLOB));

                boolean unlocked = false;
                if (unLocked == 1) unlocked = true;
                Drawing r = new Drawing(id, code, unlocked);
                r.setImg(img);
                drawings.add(r);

            } while (c.moveToNext());
            // i czyscimy liste
        }
        db.close();
        return drawings;
    }

    public void updateDrawing(int id) {
        String update = "UPDATE " + consts.TABLE_DRAWINGS_NAME +
                " SET " + consts.COLUMN_UNLOCKED + " = 1 " +
                "WHERE " + consts.COLUMN_ID + " = " + id + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(update);
        db.close();
    }

    public void updateGame(int id) {
        String update = "UPDATE " + consts.TABLE_GAMES_NAME +
                " SET " + consts.COLUMN_UNLOCKED + " = 1 " +
                "WHERE " + consts.COLUMN_ID + " = " + id + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(update);
        db.close();
    }


    public void clearData() {
        String update = "UPDATE Drawings SET unlocked = 0";

        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(update);
        db.close();
    }

}
