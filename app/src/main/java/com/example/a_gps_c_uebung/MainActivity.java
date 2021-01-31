package com.example.a_gps_c_uebung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;

    private static final int RQ_ACCESS_FINE_LOCATION = 777;
    private boolean isGpsAllowed = false;

    Location oldlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oldlocation = null;
        registerSystemService();
        checkPermissionGPS();
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
        if(isGpsAllowed) locationManager.removeUpdates(locationListener);
    }

    private void checkPermissionGPS() {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if(ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{ permission },
                    RQ_ACCESS_FINE_LOCATION);
            isGpsAllowed = false;
        }else {
            isGpsAllowed = true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode != RQ_ACCESS_FINE_LOCATION) return;
        if(grantResults.length > 0 &&
            grantResults[0] != PackageManager.PERMISSION_GRANTED){
            Toast t = Toast.makeText(getApplicationContext(), "Permission ACCES_FINE_LOACTION denied!", Toast.LENGTH_SHORT);
            t.show();
        }else{
            gpsIsGranted();
        }
    }

    private void gpsIsGranted() {
        Toast t = Toast.makeText(getApplicationContext(), "gpsGranted",Toast.LENGTH_LONG);
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
        if(oldlocation == null || location.distanceTo(oldlocation) >= 5){
            oldlocation = location;

            TextView tvx = findViewById(R.id.textX);
            TextView tvy = findViewById(R.id.textY);
            TextView tvdatetime = findViewById(R.id.textDateTime);

            tvx.setText("" + location.getLatitude());
            tvy.setText("" + location.getLongitude());
            LocalDateTime ldt =LocalDateTime.now();
            tvdatetime.setText("" + ldt.toString());

        }
    }


    private void registerSystemService() {
        locationManager = (LocationManager) getSystemService(LocationManager.class);
    }
}