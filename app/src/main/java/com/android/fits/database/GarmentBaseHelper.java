package com.android.fits.database;

import com.android.fits.database.GarmentDbSchema.GarmentTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GarmentBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public GarmentBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**(
     * Create the database here.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + GarmentTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                GarmentTable.Cols.UUID + ", " +
                GarmentTable.Cols.DESCRIPTION + ", " +
                GarmentTable.Cols.DATE + ", " +
                GarmentTable.Cols.SIZE + ", " +
                GarmentTable.Cols.TYPE + ", " +
                GarmentTable.Cols.GARMENT_TYPE +
                ")"
        );

    }

    /**
     * Update the database version here.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
