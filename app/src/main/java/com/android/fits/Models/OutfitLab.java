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
     * Add outfit to database in OutfitTable.
     * @param outfit
     */
    public void addOutfit(Outfit outfit){
        ContentValues values = getContentValuesOutfits(outfit);
        mDatabase.insert(OutfitTable.NAME, null, values);
    }

    /**
     * Add garments to outfits in Outfit_Garment_Relation table
     * @param outfitId
     * @param garmentId
     */
    public void addGarmentsToOutfits(UUID outfitId, UUID garmentId){
        ContentValues values = getContentValuesOutfitRelatedGarments(outfitId, garmentId);
        mDatabase.insert(Outfit_Garment_Relation.NAME, null, values);

    }

    /**
     * Removes garments from outfits in Outfit_Garment_Relation table.
     * @param outfitId
     * @param garmentId
     */
    public void removeGarmentsFromOutfits(UUID outfitId, UUID garmentId){
        ContentValues values = getContentValuesOutfitRelatedGarments(outfitId, garmentId);
        mDatabase.delete(Outfit_Garment_Relation.NAME, Outfit_Garment_Relation.Cols.UUID + " = ? and " + Outfit_Garment_Relation.Cols.GARMENT_UUID + " = ? ",
                new String[]{outfitId.toString(), garmentId.toString()});
    }

    /**
     * Queries the database and retrieves all the outfits in the db.
     * @return
     */
    public List<Outfit> getOutfits(){
        List<Outfit> list = new ArrayList<>();
        FitsCursorWrapper cursorWrapper = queryOutfits(null, null);

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

    /**
     * Returns a list of garments related to the outfit.
     * @param outfitId
     * @param context
     * @return list
     */
    public List<Garment> getGarmentsRelatedToOutfit(UUID outfitId, Context context){
        List<Garment> list = new ArrayList<>();
        FitsCursorWrapper cursorWrapper = queryOutfitRelatedGarments(
                Outfit_Garment_Relation.Cols.UUID + "= ?",
                new String[] {outfitId.toString()});
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                UUID garmentId = cursorWrapper.getOutfitRelatedGarmentsId();
                list.add(GarmentLab.get(context).getGarment(garmentId));
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return list;
    }

    /**
     * Returns the correct number of outfits.
     * @return int size.
     */
    public int getSize(){
        return getOutfits().size();
    }

    /**
     * Queries the database OutfitTable.
     */
    private FitsCursorWrapper queryOutfits(String whereClause, String[] whereArgs){
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
     * Queries the database Outfit_Garment_Relation table.
     */
    private FitsCursorWrapper queryOutfitRelatedGarments(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                DbSchema.Outfit_Garment_Relation.NAME,
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
     * @return values
     */
    private static ContentValues getContentValuesOutfits(Outfit outfit){
        ContentValues values = new ContentValues();
        values.put(OutfitTable.Cols.UUID,
                outfit.getUUID().toString());

        values.put(OutfitTable.Cols.OUTFIT_NAME,
                outfit.getOutfitName());

        values.put(OutfitTable.Cols.DATE,
                outfit.getDate().getTime());

        return values;
    }

    /**
     * Maps outfitId and garmentId to content values for the Outfit_Garment_Relation Table.
     * @param outfitId
     * @param garmentId
     * @return values
     */
    private static ContentValues getContentValuesOutfitRelatedGarments(UUID outfitId, UUID garmentId){
        ContentValues values = new ContentValues();

        values.put(Outfit_Garment_Relation.Cols.UUID, outfitId.toString());
        values.put(Outfit_Garment_Relation.Cols.GARMENT_UUID, garmentId.toString());

        return values;
    }


}
