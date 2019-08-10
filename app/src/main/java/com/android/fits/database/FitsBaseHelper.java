package com.android.fits.database;

import com.android.fits.Models.Garment;
import com.android.fits.database.DbSchema.GarmentTable;
import com.android.fits.database.DbSchema.Outfit_Garment_Relation;
import com.android.fits.database.DbSchema.OutfitTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FitsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "fitsBase.db";

    public FitsBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**(
     * Create the database and tables here.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + GarmentTable.NAME + "(" +
                GarmentTable.Cols.UUID + " primary key, " +
                GarmentTable.Cols.DESCRIPTION + ", " +
                GarmentTable.Cols.DATE + ", " +
                GarmentTable.Cols.SIZE + ", " +
                GarmentTable.Cols.TYPE + ", " +
                GarmentTable.Cols.GARMENT_TYPE +
                ")"
        );

        db.execSQL("create table " + OutfitTable.NAME + "(" +
                OutfitTable.Cols.UUID + " primary key, " +
                OutfitTable.Cols.OUTFIT_NAME +
                ")"
        );

        db.execSQL(
                "create table " + Outfit_Garment_Relation.NAME + "(" +
                Outfit_Garment_Relation.Cols.UUID + ", " +
                Outfit_Garment_Relation.Cols.GARMENT_UUID + ", " +
                " primary key (" + Outfit_Garment_Relation.Cols.UUID + " , " + Outfit_Garment_Relation.Cols.GARMENT_UUID + " ) ," +
                " FOREIGN KEY ("+ Outfit_Garment_Relation.Cols.UUID +") " +
                "REFERENCES "+ OutfitTable.NAME+"("+ OutfitTable.Cols.UUID+ ")," +
                " FOREIGN KEY ("+ Outfit_Garment_Relation.Cols.GARMENT_UUID +") " +
                "REFERENCES "+ GarmentTable.NAME+"("+ GarmentTable.Cols.UUID + "))"
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
