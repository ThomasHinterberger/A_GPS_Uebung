package com.example.a_gps_c_uebung;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a_gps_c_uebung.adapter.Gps_adapter;
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
            String rowStr = "longitude = " + longitude + " \nlatitude =" + latitude + " \nmyDate =" + dateTime;
            arrayList.add(rowStr);
        }

        Gps_adapter gps_adapter = new Gps_adapter(getApplicationContext(), R.layout.list_view_gps_layout, arrayList);
        ListView listView = findViewById(R.id.listViewGpsData);
        listView.setAdapter(gps_adapter);
    }
}