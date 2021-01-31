package com.example.a_gps_c_uebung.database;

public class GPSTbl {
    public static final String GPSTABLE_NAME = "GPS";

    public static final String GPSID = "GPSID";
    public static final String Longitude = "Longitude";
    public static final String Latitude = "Latitude";
    public static final String DateTime = "DateTime";

    public static final String GPSSQL_CREATE =
            "CREATE TABLE " + GPSTABLE_NAME +
                    " (" +
                    GPSID + " INTEGER PRIMARY KEY,"+
                    Longitude + " TEXT NOT NULL," +
                    Latitude + " TEXT NOT NULL," +
                    DateTime + " TEXT NOT NULL" +
                    ")";

    public static final String GPSSTMT_INSERT =
            "INSERT INTO " + GPSTABLE_NAME +
                    " (" + GPSID + ", " + Longitude + ", " + Latitude + ", " + DateTime + ")" +
                    " VALUES (?,?,?,?)";

    public static final String GPSSTMT_COUNT =
            "SELECT COUNT(*) FROM " + GPSTABLE_NAME;

    public static final String GPSSTMT_SELECT =
            "SELECT * FROM " + GPSTABLE_NAME;
}
