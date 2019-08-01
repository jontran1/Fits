package com.android.fits.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GarmentLab {
    private static GarmentLab sGarmentLab;
    private HashMap<UUID ,Garment> mGarments;


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
        mGarments = new HashMap<UUID, Garment>();
        for (int i = 0; i < 50; i ++){
            if (i%2 == 0){
                Top top = new Top();
                mGarments.put(top.getId() ,top);
                mGarments.get(top.getId()).setBrand("Nike");
                mGarments.get(top.getId()).setColor("Black");
                mGarments.get(top.getId()).setSize(Top.TopSize.Large.toString());
                mGarments.get(top.getId()).setDescription("Black Jacket with black and white buttons " + i);
                mGarments.get(top.getId()).setType(Top.TopType.Bomber.toString());
                mGarments.get(top.getId()).setStore("K-Mart");
            }else {
                Pants pants = new Pants();
                mGarments.put(pants.getId(), pants);
                mGarments.get(pants.getId()).setBrand("Nike");
                mGarments.get(pants.getId()).setColor("Black");
                mGarments.get(pants.getId()).setSize(Pants.PantsSize.thirtyeight_by_thirtytwo.getName());
                mGarments.get(pants.getId()).setDescription("Navy Blue jeans with light tares " + i);
                mGarments.get(pants.getId()).setType("Chinos");
                mGarments.get(pants.getId()).setStore("K-Mart");
            }
        }
    }

    /**
     * Get a list of garment objects.
     * @return
     */
    public List<Garment> getGarments() {
        return new ArrayList<>(mGarments.values());
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
}
