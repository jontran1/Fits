package com.android.fits.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.fits.database.DbSchema;
import com.android.fits.database.DbSchema.OutfitTable;
import com.android.fits.database.DbSchema.Outfit_Garment_Relation;


import com.android.fits.database.FitsBaseHelper;
import com.android.fits.database.FitsCursorWrapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class OutfitLab {
    public static OutfitLab mOutfitLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private LinkedHashMap<UUID, Outfit> mOutfits;

    public static OutfitLab get(Context context){
        if (mOutfitLab == null){
            mOutfitLab = new OutfitLab(context);
        }
        return mOutfitLab;
    }

    private OutfitLab(Context context){
        this.mContext = context.getApplicationContext();
        this.mDatabase = new FitsBaseHelper(mContext).getWritableDatabase();
        this.mOutfits = new LinkedHashMap<>();
    }

    /**
     * Add outfit to database.
     * @param outfit
     */
    public void addOutfit(Outfit outfit){
        ContentValues values = getContentValues(outfit);
        mDatabase.insert(OutfitTable.NAME, null, values);
    }

    /**
     * Queries the database and retrieves all the outfits in the db.
     * @return
     */
    public List<Outfit> getOutfits(){
        List<Outfit> list = new ArrayList<>();
        FitsCursorWrapper cursorWrapper = queryGarments(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                list.add(cursorWrapper.getOutfit());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return list;

    }

    private FitsCursorWrapper queryGarments(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                OutfitTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new FitsCursorWrapper(cursor);
    }

    /**
     * Maps Outfits to ContentValues. ContentValues are then
     * passed into the database to update.
     * @param outfit
     * @return
     */
    private static ContentValues getContentValues(Outfit outfit){
        ContentValues values = new ContentValues();
        values.put(DbSchema.OutfitTable.Cols.UUID,
                outfit.getUUID().toString());

        values.put(DbSchema.OutfitTable.Cols.OUTFIT_NAME,
                outfit.getOutfitName());

        return values;
    }


}
