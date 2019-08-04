package com.android.fits.Models;

import com.android.fits.TypeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shoes extends Garment {
    public enum ShoeType{
        Sneakers("Sneakers"),
        Low_Top_Sneakers("Low Top Sneakers"),
        High_Top_Sneakers("High Top Sneakers"),
        Tennis_Shoes("Tennis Shoes"),
        Running_Shoes("Running Shoes"),
        Dress_Shoes("Dress Shoes"),
        Boots("Boots");

        private String name;

        ShoeType(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum ShoeSize{
        seven("7"),
        seven_point_five("7.5"),
        eight("8"),
        eight_point_five("8.5"),
        nine("9"),
        nine_point_five("9.5"),
        ten("10"),
        ten_point_five("10.5"),
        eleven("11"),
        eleven_point_five("11.5"),
        twelve("12"),
        twelve_point_five("12.5"),
        thirteen("13"),
        thirteen_point_five("13.5"),
        fourteen("14"),
        fifteen("15");

        private String name;
        ShoeSize(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    protected Shoes(){
        super();
    }
    protected Shoes(UUID id){
        super(id);
    }

    @Override
    public String getGarmentType(){
        return TypeUtil.Type.Shoes.toString();
    }

    /**
     * Returns a list of sizes specific to that garment type
     * @return sizes
     */
    @Override
    public List<String> getSizes(){
        List<String> sizes = new ArrayList<>();
        for (ShoeSize size: ShoeSize.values()){
            sizes.add(size.toString());
        }
        return sizes;
    }

    /**
     * Returns a list of pants types
     * @return types
     */
    @Override
    public List<String> getTypes(){
        List<String> types = new ArrayList<>();
        for (ShoeType type : ShoeType.values()){
            types.add(type.toString());
        }
        return types;
    }
}
