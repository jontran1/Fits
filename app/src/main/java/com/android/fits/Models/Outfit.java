package com.android.fits.Models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Outfit {
    private UUID mUUID;
    private List<UUID> mGarmentsList;
    private String outfitName;
    private Date mDate;

    public Outfit(String outfitName){
        this.mUUID = UUID.randomUUID();
        this.outfitName = outfitName;
        this.mDate = new Date();
    }

    public Outfit(String outfitName, UUID id, Date date){
        this.mUUID = id;
        this.outfitName = outfitName;
        this.mDate = date;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID UUID) {
        mUUID = UUID;
    }

    public List<UUID> getGarmentsList() {
        return mGarmentsList;
    }

    public void setGarmentsList(List<UUID> garmentsList) {
        mGarmentsList = garmentsList;
    }

    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfitName(String outfitName) {
        this.outfitName = outfitName;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return outfitName;
    }
}
