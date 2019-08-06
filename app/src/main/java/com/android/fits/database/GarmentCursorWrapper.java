package com.android.fits.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import com.android.fits.TypeUtil;
import com.android.fits.database.DbSchema.GarmentTable;
import com.android.fits.Models.Garment;

import java.util.Date;
import java.util.UUID;

public class GarmentCursorWrapper extends CursorWrapper {

    public GarmentCursorWrapper(Cursor cursor){
        super(cursor);
    }

    /**
     * Retrieves the crime from the cursor and creates a new Crime
     * using the information from the cursor. The crime is then returned.
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
}
