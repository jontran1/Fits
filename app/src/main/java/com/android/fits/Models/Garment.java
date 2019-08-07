package com.android.fits.Models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.android.fits.TypeUtil.Type;


public abstract class Garment {

    private UUID mId;
    private Date mDate;
    private String mDescription;
    private String mSize;
    private String mType;




    protected Garment(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    protected Garment(UUID id){
        mId = id;
    }

    /**
     * Creates a new garment object from the database. That is because
     * when creating an object from the database we need to set the
     * UUID id member variable.
     * @param garmentType
     * @param id
     * @return
     */
    public static Garment createNewGarmentFromDataBase(Type garmentType,  UUID id){
        Garment garment;
        switch (garmentType){
            case Hats:
                garment = new Hat(id);
                break;
            case Top:
                garment = new Top(id);
                break;
            case Pants:
                garment = new Pants(id);
                break;
            default :
                garment = new Shoes(id);
        }
        return garment;
    }

    /**
     * When a new garment is created in memory.
     * @param type
     * @return
     */
    public static Garment createNewGarment(Type type){
        Garment garment;
        switch (type){
            case Hats:
                garment = new Hat();
                break;
            case Top:
                garment = new Top();
                break;
            case Pants:
                garment = new Pants();
                break;
            default :
                garment = new Shoes();
        }
        return garment;
    }



    public abstract List<String> getSizes();

    public abstract List<String> getTypes();

    public abstract String getGarmentType();

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

    public void setDate(Date date) {
        mDate = date;
    }

    public String getSize(){
        return mSize;
    }

    public void setSize(String size){
        this.mSize = size;
    }


    public String getDescription(){
        return this.mDescription;
    }

    public void setDescription(String description){
        this.mDescription = description;
    }

    public String getPhotoFileName(){
        return "IMG_" + getId().toString() + ".jpg";
    }

    @Override
    public String toString(){
        return getId().toString();
    }
}
