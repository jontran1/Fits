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




    public Garment(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Garment(UUID id){
        mId = id;
    }

    public static Garment createNewGarmentFromDataBase(Type garmentType, String type,  UUID id){
        Garment garment;
        switch (garmentType){
            case Hats:
                garment = new Hat(id);
                garment.setType((type).toString());
                break;
            case Top:
                garment = new Top(id);
                garment.setType((type).toString());

                break;
            case Pants:
                garment = new Pants(id);
                garment.setType((type).toString());

                break;
            default :
                garment = new Shoes(id);
                garment.setType((type).toString());

        }
        return garment;
    }

    public static Garment createNewGarment(Type type){
        Garment garment;
        switch (type){
            case Hats:
                garment = new Hat();
                garment.setType(Hat.HatType.Dad_Hat.toString());
                break;
            case Top:
                garment = new Top();
                garment.setType(Top.TopType.Shirt.toString());
                break;
            case Pants:
                garment = new Pants();
                garment.setType(Pants.PantsType.Jeans.toString());
                break;
            default :
                garment = new Shoes();
                garment.setType(Shoes.ShoeType.Sneakers.toString());
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

}
