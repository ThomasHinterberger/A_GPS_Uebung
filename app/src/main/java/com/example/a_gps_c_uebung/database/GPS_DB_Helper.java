package com.example.a_gps_c_uebung.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GPS_DB_Helper extends SQLiteOpenHelper {

    private final static String DB_NAME = "gps.db";
    private final static int DB_VERSION = 1;

    public GPS_DB_Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GPSTbl.GPSSQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
