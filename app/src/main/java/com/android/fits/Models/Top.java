package com.android.fits.Models;

import java.util.ArrayList;
import java.util.List;

public class Top extends Garment {

    public static enum TopType{
        Other, Bomber, Jacket, Shirt, Jean_Jacket, Flannel, Hoodie, Sweater
    }

    public static enum TopSize{
        Other, XSmall, Small, Medium, Large, XLarge, XXLarge, XXXLarge
    }

    public Top(){
        super();
    }


    /**
     * Returns a list of sizes specific to that garment type
     * @return sizes
     */
    @Override
    public  List<String> getSizes(){
        List<String> sizes = new ArrayList<>();
            for (TopSize size: TopSize.values()){
                sizes.add(size.toString());
            }
        return sizes;
    }

    /**
     * Returns a list of top types
     * @return types
     */
    @Override
    public List<String> getTypes(){
        List<String> types = new ArrayList<>();
        for (TopType type : TopType.values()){
            types.add(type.toString());
        }
        return types;
    }
}
