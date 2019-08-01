package com.android.fits.Models;

import java.util.ArrayList;
import java.util.List;

public class Hat extends Garment {

    public static enum HatType{
        Dad_Hat, Snap_Back, Beanie
    }

    public static enum HatSize{
        Size_1, Size_2, Size_3, Size_4
    }

    public Hat(){
        super();
    }

    /**
     * Returns a list of sizes specific to that garment type
     * @return sizes
     */
    @Override
    public List<String> getSizes(){
        List<String> sizes = new ArrayList<>();
        for (HatSize size: HatSize.values()){
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
        for (HatType type : HatType.values()){
            types.add(type.toString());
        }
        return types;
    }
}
