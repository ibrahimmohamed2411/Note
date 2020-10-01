package com.example.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME="notes.db";
    public static final String TABLE_NAME="note";
    public static final String COL_ID="id";
    public static final String COL_NOTE_NAME="note_name";
    public static final String COL_TAG="tag";
    public static final String COL_NOTE="note";
    public static final String COL_IMAGE="image";


    private static final int DB_VERSION=2;


    public MyDatabase(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+ COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+ COL_NOTE_NAME+" TEXT ,"+COL_TAG+" TEXT ,"+COL_NOTE+ " TEXT ,"+ COL_IMAGE+" TEXT )" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }







}
