package com.android.fits.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.fits.database.GarmentDbSchema.GarmentTable;

import com.android.fits.database.GarmentBaseHelper;
import com.android.fits.database.GarmentCursorWrapper;
import com.android.fits.database.GarmentDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class GarmentLab {

    private static GarmentLab sGarmentLab;
    private LinkedHashMap<UUID ,Garment> mGarments;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    /**
     * A static method that returns a singleton object.
     *
     * @param context
     * @return
     */
    public static GarmentLab get(Context context){
        if (sGarmentLab == null){
            sGarmentLab = new GarmentLab(context);
        }
        return sGarmentLab;
    }


    /**
     * Returns a list of Garment objects.
     * @param context
     */
    private GarmentLab (Context context){
        this.mContext = context.getApplicationContext();
        /**
         * This function getWritableDatabase() will open the database,
         * or create. If it opens it checks to see if it should update.
         */
        mDatabase = new GarmentBaseHelper(mContext).getWritableDatabase();
        mGarments = new LinkedHashMap<>();

    }

    /**
     * Get a list of garment objects.
     * @return
     */
    public List<Garment> getGarments() {
        List<Garment> list = new ArrayList<>();
        GarmentCursorWrapper cursorWrapper = queryGarments(null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                list.add(cursorWrapper.getGarment());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return list;
    }

    /**
     * Returns a specific garment from the GarmentLab
     * using UUID id.
     * @param id
     * @return
     */
    public Garment getGarment(UUID id){
        GarmentCursorWrapper cursor = queryGarments(GarmentTable.Cols.UUID + "= ?",
                new String[] {id.toString()});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getGarment();
        }finally {
            cursor.close();
        }
    }

    /**
     * Add garment item to db.
     * @param garment
     */
    public void addGarment(Garment garment){
        ContentValues values = getContentValues(garment);
        mDatabase.insert(GarmentTable.NAME, null, values);
    }

    /**
     * Updates the crime in the database.
     * @param garment
     */
    public void updateGarment(Garment garment){
        String uuidString = garment.getId().toString();
        ContentValues values = getContentValues(garment);

        mDatabase.update(GarmentTable.NAME, values, GarmentTable.Cols.UUID + " = ? ",
                new String[] { uuidString });
    }



    public int getSize(){
        return mGarments.size();
    }

    /**
     * GarmentLab is responsible for everything related to
     * persisting data. This method provides a complete local filepath
     * for the garment's image.
     * @param garment
     * @return
     */
    public File getPhotoFile(Garment garment){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, garment.getPhotoFileName());
    }

    private GarmentCursorWrapper queryGarments(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                GarmentDbSchema.GarmentTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new GarmentCursorWrapper(cursor);
    }

    /**
     * This method maps Crime into ContentValues. This creates
     * ContentValues instances from Crimes.
     * @param garment
     * @return
     */
    private static ContentValues getContentValues(Garment garment){
        ContentValues values = new ContentValues();
        values.put(GarmentTable.Cols.UUID,
                garment.getId().toString());

        values.put(GarmentTable.Cols.DESCRIPTION,
                garment.getDescription());

        values.put(GarmentTable.Cols.DATE,
                garment.getDate().getTime());

        values.put(GarmentTable.Cols.SIZE,
                garment.getSize());

        values.put(GarmentTable.Cols.GARMENT_TYPE,
                garment.getGarmentType());

        values.put(GarmentTable.Cols.TYPE,
                garment.getType());


        return values;
    }
}
