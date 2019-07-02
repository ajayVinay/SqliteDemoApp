package com.example.uipl.sqlitedemoapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class NewHalperclass extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Db_userNames";
    private static final int DATABASE_VERSION = 1;
    public static final String TABEL_NAME ="user";


    public static final  String USER_ID ="id";
    public static final  String USER_NAME ="name";
    public static final  String USER_NUMBER ="number";

    public NewHalperclass( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private String TABLE_CREATE = "create tabel" + TABEL_NAME+ "("+ USER_ID +"integer primary key," +
                                                                    USER_NAME +"text," +
                                                                    USER_NUMBER+ "text)";



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
