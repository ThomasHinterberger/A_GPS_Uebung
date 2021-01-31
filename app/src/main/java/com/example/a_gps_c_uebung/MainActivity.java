package com.example.a_gps_c_uebung;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.a_gps_c_uebung.database.GPSTbl;
import com.example.a_gps_c_uebung.database.GPS_DB_Helper;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity {
    private static final int RQ_ACCESS_FINE_LOCATION = 777;
    LocationManager locationManager;
    LocationListener locationListener;
    SQLiteDatabase db;
    Location oldlocation;
    private boolean isGpsAllowed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database
        GPS_DB_Helper db_helper = new GPS_DB_Helper(this);
        db = db_helper.getReadableDatabase();

        oldlocation = null;
        registerSystemService();
        checkPermissionGPS();
        setonClickListener();
    }



    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        super.onPostResume();
        if (isGpsAllowed) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    3000,
                    0,
                    locationListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isGpsAllowed) locationManager.removeUpdates(locationListener);
    }

    private void checkPermissionGPS() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    RQ_ACCESS_FINE_LOCATION);
            isGpsAllowed = false;
        } else {
            isGpsAllowed = true;
            gpsIsGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RQ_ACCESS_FINE_LOCATION) return;
        if (grantResults.length > 0 &&
                grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast t = Toast.makeText(getApplicationContext(), "Permission ACCES_FINE_LOACTION denied!", Toast.LENGTH_SHORT);
            t.show();
        } else {
            gpsIsGranted();
        }
    }

    private void gpsIsGranted() {
        Toast t = Toast.makeText(getApplicationContext(), "gpsGranted", Toast.LENGTH_LONG);
        t.show();
        isGpsAllowed = true;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                displayLocation(location);
            }
        };
    }

    private void displayLocation(Location location) {
        if (oldlocation == null || location.distanceTo(oldlocation) >= 5) {
            oldlocation = location;
            //database
            Cursor rows = db.rawQuery(GPSTbl.GPSSTMT_COUNT, null);
            rows.moveToNext();
            int id = rows.getInt(0);
            db.execSQL(GPSTbl.GPSSTMT_INSERT, new Object[]{id, "" + location.getLongitude(),
                    "" + location.getLatitude(), LocalDateTime.now().toString()});

            TextView tvx = findViewById(R.id.textX);
            TextView tvy = findViewById(R.id.textY);
            TextView tvdatetime = findViewById(R.id.textDateTime);

            tvx.setText("" + location.getLatitude());
            tvy.setText("" + location.getLongitude());
            LocalDateTime ldt = LocalDateTime.now();
            tvdatetime.setText("" + ldt.toString());

        }
    }


    private void registerSystemService() {
        locationManager = getSystemService(LocationManager.class);
    }

    private void setonClickListener() {
        Button button = findViewById(R.id.buttonAnzeigen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Gps_show_data.class);
                startActivity(intent);
            }
        });
    }
}