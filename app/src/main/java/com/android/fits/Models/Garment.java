package com.android.fits.Models;

import java.util.Date;
import java.util.UUID;

public abstract class Garment {

    private UUID mId;
    private Date mDate;

    private String mColor;
    private String mBrand;
    private String mStore;
    private String mDescription;

    public Garment(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId(){
        return mId;
    }

    public Date getDate(){
        return mDate;
    }

    public abstract String getSize();

    public abstract void setSize(String size);

    public String getColor(){
        return mColor;
    }

    public void setColor(String color){
        this.mColor = color;
    }

    public String getBrand(){
        return this.mBrand;
    }

    public void setBrand(String brand){
        this.mBrand = mBrand;
    }

    public String getStore(){
        return this.mStore;
    }

    public void setStore(String store){
        this.mStore = store;
    }

    public String getDescription(){
        return this.mDescription;
    }

    public void setDescription(String description){
        this.mDescription = description;
    }

}
