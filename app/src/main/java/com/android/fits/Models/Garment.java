package com.android.fits.Models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.android.fits.TypeUtil.Type;


public abstract class Garment {

    private UUID mId;
    private Date mDate;

    private String mBrand;
    private String mStore;
    private String mDescription;
    private String mSize;
    private String mType;




    public Garment(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public static Garment createGarment(Type type){
        Garment garment;
        switch (type){
            case Hats:
                garment = new Hat();
                garment.setSize(Hat.HatSize.Size_1.toString());
                garment.setType(Hat.HatType.Dad_Hat.toString());
                break;
            case Top:
                garment = new Top();
                garment.setSize(Top.TopSize.XSmall.toString());
                garment.setType(Top.TopType.Shirt.toString());
                break;
            case Pants:
                garment = new Pants();
                garment.setSize(Pants.PantsSize.thirty_by_thirty.toString());
                garment.setType(Pants.PantsType.Jeans.toString());
                break;
            default :
                garment = new Shoe();
                garment.setSize(Shoe.ShoeSize.Size_1.toString());
                garment.setType(Shoe.ShoeType.Sneakers.toString());
        }
        return garment;
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


    public String getBrand(){
        return this.mBrand;
    }

    public void setBrand(String brand){
        this.mBrand = brand;
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

    public String getPhotoFileName(){
        return "IMG_" + getId().toString() + ".jpg";
    }

}
