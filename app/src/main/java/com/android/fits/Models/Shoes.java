package com.android.fits.Models;

import com.android.fits.TypeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shoes extends Garment {
    public enum ShoeType{
        Sneakers, Running_Shoes, Dress_Shoes,
    }

    public enum ShoeSize{
        Size_1, Size_2, Size_3, Size_4
    }

    protected Shoes(){
        super();
    }
    protected Shoes(UUID id){
        super(id);
    }

    @Override
    public String getGarmentType(){
        return TypeUtil.Type.Shoes.toString();
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
