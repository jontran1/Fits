package com.android.fits.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Top extends Garment {

    private Type type;

    public static enum Type{
        Bomber, Jacket, Shirt,
    }

    public Top(){
        super();
    }

    public void setType(Type type){
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

}
