package com.android.fits.Models;

import com.android.fits.TypeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Top extends Garment {

    public enum TopType{
        Bomber("Bomber"),
        Jacket("Jacket"),
        Shirt("Shirt"),
        Jean_Jacket("Jean Jacket"),
        Flannel("Flannel"),
        Hoodie("Hoodie"),
        Sweater("Sweater");

        private String name;
        TopType(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum TopSize{
        XSmall, Small, Medium, Large, XLarge, XXLarge, XXXLarge
    }

    protected Top(){
        super();
    }
    protected Top(UUID id){
        super(id);
    }

    @Override
    public String getGarmentType(){
        return TypeUtil.Type.Top.toString();
    }


    /**
     * Returns a list of sizes specific to that garment type
     * @return sizes
     */
    @Override
    public  List<String> getSizes(){
        List<String> sizes = new ArrayList<>();
            for (TopSize size: TopSize.values()){
                sizes.add(size.toString());
            }
        return sizes;
    }

    /**
     * Returns a list of top types
     * @return types
     */
    @Override
    public List<String> getTypes(){
        List<String> types = new ArrayList<>();
        for (TopType type : TopType.values()){
            types.add(type.toString());
        }
        return types;
    }
}
