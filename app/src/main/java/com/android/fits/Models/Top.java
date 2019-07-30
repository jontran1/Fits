package com.android.fits.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Top extends Garment {

    private String type;
    private String mSize;

    public static enum Type{
        Bomber, Jacket, Shirt,
    }

    public static enum Size{
        XSmall, Small, Medium, Large, XLarge, XXLarge, XXXLarge
    }

    public Top(){
        super();
    }

    @Override
    public String getSize(){
        return mSize;
    }

    @Override
    public void setSize(String size){
        this.mSize = size;
    }

    public void setType(String type){
        this.type = type;
    }

    /**
     * Return a string representation of the type.
     * @return
     */
    public String getType(){
        return this.type.toString();
    }

    /**
     * Returns a list of types represented as strings.
     * @return
     */
    public static List getTypes(){
        List types = new ArrayList();
        for (Type type : Type.values()){
            types.add(type.toString());
        }
        return types;
    }

    /**
     * Returns a list of sizes represented as strings.
     * @return
     */
    public static List getSizes(){
        List sizes = new ArrayList();
        for (Size size : Size.values()){
            sizes.add(size.toString());
        }
        return sizes;
    }

}
