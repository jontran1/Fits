package com.android.fits.Models;

import java.util.List;
import java.util.UUID;

public class Outfit {
    private UUID mUUID;
    private List<UUID> mGarmentsList;
    private String outfitName;

    public Outfit(String outfitName){
        this.mUUID = UUID.randomUUID();
        this.outfitName = outfitName;
    }

    public Outfit(String outfitName, UUID id){
        this.mUUID = id;
        this.outfitName = outfitName;
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

    @Override
    public String toString() {
        return outfitName;
    }
}
