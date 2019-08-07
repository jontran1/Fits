package com.android.fits.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.fits.Models.Outfit;
import com.android.fits.TypeUtil;
import com.android.fits.database.DbSchema.GarmentTable;
import com.android.fits.database.DbSchema.OutfitTable;
import com.android.fits.database.DbSchema.Outfit_Garment_Relation;


import com.android.fits.Models.Garment;

import java.util.Date;
import java.util.UUID;

public class FitsCursorWrapper extends CursorWrapper {

    public FitsCursorWrapper(Cursor cursor){
        super(cursor);
    }

    /**
     * Retrieves the garment from the cursor and creates a new garment
     * using the information from the cursor. The garment is then returned.
     * @return crime
     */
    public Garment getGarment(){
        String uuidString = getString(getColumnIndex(GarmentTable.Cols.UUID));
        String description = getString(getColumnIndex(GarmentTable.Cols.DESCRIPTION));
        long date = getLong(getColumnIndex(GarmentTable.Cols.DATE));
        String size = getString(getColumnIndex(GarmentTable.Cols.SIZE));
        String type = getString(getColumnIndex(GarmentTable.Cols.TYPE));
        String garmentType = getString(getColumnIndex(GarmentTable.Cols.GARMENT_TYPE));

        Garment garment = Garment.createNewGarmentFromDataBase(TypeUtil.Type.valueOf(garmentType), UUID.fromString(uuidString));
        garment.setDescription(description);
        garment.setSize(size);
        garment.setDate(new Date(date));
        garment.setType(type);

        return garment;
    }

    /**
     * Retrieves the outfit from the database and returns the outfit.
     * @return
     */
    public Outfit getOutfit(){
        String uuidString = getString(getColumnIndex(OutfitTable.Cols.UUID));
        String outfitNameString = getString(getColumnIndex(OutfitTable.Cols.OUTFIT_NAME));
        Outfit outfit = new Outfit(outfitNameString, UUID.fromString(uuidString));
        return outfit;
    }

    /**
     * Retrieves the garments related to the outfit.
     * @return
     */
    public UUID getOutfitRelatedGarmentsId(){
        String uuidString = getString(getColumnIndex(Outfit_Garment_Relation.Cols.GARMENT_UUID));
        System.out.println("getOutfitRelatedGarmentsId(): " + uuidString);
        return UUID.fromString(uuidString);
    }
}
