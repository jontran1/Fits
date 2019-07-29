package com.android.fits.Models;

import java.util.Date;
import java.util.UUID;

public class Garment {

    private UUID mId;
    private Date mDate;

    private String mSize;
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

    public String getSize(){
        return mSize;
    }

    public void setSize(String size){
        this.mSize = size;
    }

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
