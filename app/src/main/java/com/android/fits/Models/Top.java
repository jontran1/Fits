package com.android.fits.Models;

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

    public String getType(){
        return this.type.toString();
    }

}
