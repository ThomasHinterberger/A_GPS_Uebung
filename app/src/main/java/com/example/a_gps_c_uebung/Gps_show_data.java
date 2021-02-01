package com.example.a_gps_c_uebung;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a_gps_c_uebung.database.GPSTbl;
import com.example.a_gps_c_uebung.database.GPS_DB_Helper;

import java.util.ArrayList;

public class Gps_show_data extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_show_data);
        loadData();
    }

    private void loadData() {
        GPS_DB_Helper db_helper = new GPS_DB_Helper(this);
        SQLiteDatabase db = db_helper.getReadableDatabase();
        Cursor rows = db.rawQuery(GPSTbl.GPSSTMT_SELECT, null);
        ArrayList<String> arrayList = new ArrayList<>();

        while (rows.moveToNext()) {
            String longitude = rows.getString(1);
            String latitude = rows.getString(2);
            String dateTime = rows.getString(3);
            arrayList.add("longitude= " + longitude + " latitude=" + latitude + " myDate =" + dateTime);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
        ListView listView = findViewById(R.id.listViewGpsData);
        listView.setAdapter(arrayAdapter);
    }
}