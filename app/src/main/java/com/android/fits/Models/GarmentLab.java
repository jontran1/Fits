package com.android.fits.Models;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class GarmentLab {
    private static GarmentLab sGarmentLab;
    private LinkedHashMap<UUID ,Garment> mGarments;
    private Context mContext;

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
        this.mContext = context;
        mGarments = new LinkedHashMap<>();
    }

    /**
     * Get a list of garment objects.
     * @return
     */
    public List<Garment> getGarments() {
        return new LinkedList<>(mGarments.values());
    }

    /**
     * Returns a specific garment from the GarmentLab
     * using UUID id.
     * @param id
     * @return
     */
    public Garment getGarment(UUID id){
        return mGarments.get(id);
    }

    /**
     * Add garment item to hash map.
     * @param garment
     */
    public void addGarment(Garment garment){
        mGarments.put(garment.getId(), garment);
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
}
