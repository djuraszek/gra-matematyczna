package com.android.gramatematyczna.database;

public class ConstVals {
//    LocalDate date = LocalDate.now();
//    String dateS = date.format(DateTimeFormatter.ofPattern(ConstVals.DATE_FORMAT));

    //todo measures
    static final String TABLE_DRAWINGS_NAME = "Drawings";
    static final String TABLE_GAMES_NAME = "Games";

    static final String COLUMN_UNLOCKED = "unlocked";
    static final String COLUMN_BLOB = "image";
    static final String COLUMN_ID = "id";
    static final String COLUMN_LEVEL = "level";
    static final String COLUMN_GAME_TYPE = "type";
    static final String COLUMN_CODE = "code";


    //    *************    MEASURES  *******************
    public static final String SQL_CREATE_TABLE_GAMES =        //add, get
            "CREATE TABLE " + TABLE_GAMES_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_UNLOCKED + " INTEGER,"
                    + COLUMN_LEVEL + " INTEGER,"
                    + COLUMN_GAME_TYPE + " INTEGER,"
                    + COLUMN_BLOB + " BLOB"
                    + "); ";

    //    *************    WEIGHT  *******************
    public static final String SQL_CREATE_TABLE_DRAWINGS =        //add, get
            "CREATE TABLE " + TABLE_DRAWINGS_NAME + " ( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_UNLOCKED + " INTEGER,"
                    + COLUMN_CODE + " TEXT,"
                    + COLUMN_BLOB + " BLOB"
                    + "); ";

}
