package com.android.fits.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GarmentLab {
    private static GarmentLab sGarmentLab;
    private List<Garment> mGarments;


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
        mGarments = new ArrayList<>();
        for (int i = 0; i < 50; i ++){
            mGarments.add(new Garment());
            mGarments.get(i).setBrand("Wall-Mart");
            mGarments.get(i).setColor("Black");
            mGarments.get(i).setSize("M");
            mGarments.get(i).setDescription("Black Jacket with blue buttons");
        }
    }

    /**
     * Get a list of garment objects.
     * @return
     */
    public List<Garment> getGarments() {
        return mGarments;
    }

    /**
     * Returns a specific garment from the GarmentLab
     * using UUID id.
     * @param id
     * @return
     */
    public Garment getGarment(UUID id){
        for (Garment garment : mGarments){
            if (garment.getId().equals(id)){
                return garment;
            }
        }
        return null;
    }
}
