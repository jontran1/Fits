package com.android.fits.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class Garment {

    private UUID mId;
    private Date mDate;

    private String mColor;
    private String mBrand;
    private String mStore;
    private String mDescription;
    private String mSize;
    private String mType;




    public Garment(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public abstract List<String> getSizes();

    public abstract List<String> getTypes();


    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
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
