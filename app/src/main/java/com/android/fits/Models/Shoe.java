package com.android.fits.Models;

import java.util.ArrayList;
import java.util.List;

public class Shoe extends Garment {
    public static enum ShoeType{
        Sneakers, Running_Shoes, Dress_Shoes,
    }

    public static enum ShoeSize{
        Size_1, Size_2, Size_3, Size_4
    }

    public Shoe(){
        super();
    }

    /**
     * Returns a list of sizes specific to that garment type
     * @return sizes
     */
    @Override
    public List<String> getSizes(){
        List<String> sizes = new ArrayList<>();
        for (ShoeSize size: ShoeSize.values()){
            sizes.add(size.toString());
        }
        return sizes;
    }

    /**
     * Returns a list of pants types
     * @return types
     */
    @Override
    public List<String> getTypes(){
        List<String> types = new ArrayList<>();
        for (ShoeType type : ShoeType.values()){
            types.add(type.toString());
        }
        return types;
    }
}
