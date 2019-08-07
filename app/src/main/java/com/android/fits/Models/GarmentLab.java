package com.android.fits.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.android.fits.database.DbSchema.GarmentTable;

import com.android.fits.database.FitsBaseHelper;
import com.android.fits.database.FitsCursorWrapper;
import com.android.fits.database.DbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        mDatabase = new FitsBaseHelper(mContext).getWritableDatabase();
        mGarments = new LinkedHashMap<>();

    }

    /**
     * Get a list of garment objects.
     * @return
     */
    public List<Garment> getGarments() {
        List<Garment> list = new ArrayList<>();
        FitsCursorWrapper cursorWrapper = queryGarments(null, null);

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
     * @param garmentId
     * @return
     */
    public Garment getGarment(UUID garmentId){
        FitsCursorWrapper cursor = queryGarments(GarmentTable.Cols.UUID + "= ?",
                new String[] {garmentId.toString()});
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
        try {
            mDatabase.insert(GarmentTable.NAME, null, values);
        }catch (SQLiteException e){
            System.out.println("Garment Id repeated " + garment.getId().toString());
        }
    }

    /**
     * Removes garment instance out of database.
     * @param garment
     */
    public void removeGarment(Garment garment){
        mDatabase.delete(GarmentTable.NAME, GarmentTable.Cols.UUID + " = ? ", new String[]{garment.getId().toString()});
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

    private FitsCursorWrapper queryGarments(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                DbSchema.GarmentTable.NAME,
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
     * This method maps Garment into ContentValues. This creates
     * ContentValues instances from Garment.
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
