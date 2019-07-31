package com.android.fits.Models;

import java.util.ArrayList;
import java.util.List;

public class Pants extends Garment {

    public static enum PantsType{
        Jeans, Khakis, Chinos, Cargo_Pants
    }

    public static enum PantsSize {
        thirty_by_thirty("30x30"),
        thirty_by_thirtytwo("30x32"),
        thirtytwo_by_thirty("32x30"),
        thirtytwo_by_thirtytwo("32x32"),
        thirtytwo_by_thirtyfour("32x34"),
        thirtytwo_by_thirtysix("32x36"),
        thirtyfour_by_thirty("34x30"),
        thirtyfour_by_thirtytwo("34x32"),
        thirtyfour_by_thirtyfour("34x34"),
        thirtyfour_by_thirtysix("34x36"),
        thirtysix_by_thirty("34x30"),
        thirtysix_by_thirtytwo("36x32"),
        thirtysix_by_thirtyfour("36x34"),
        thirtysix_by_thirtysix("36x36"),
        thirtyeight_by_thirty("38x30"),
        thirtyeight_by_thirtytwp("38x32"),
        thirtyeight_by_thirtytwo("38x32"),
        thirtyeight_by_thirtyfour("38x34"),
        thirtyeight_by_thirtysix("38x36");

        private String name;

        PantsSize(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    public Pants(){
        super();
    }


    /**
     * Returns a list of sizes specific to that garment type
     * @return sizes
     */
    @Override
    public List<String> getSizes(){
        List<String> sizes = new ArrayList<>();
        for (Pants.PantsSize size: Pants.PantsSize.values()){
            sizes.add(size.getName());
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
        for (Pants.PantsType type : Pants.PantsType.values()){
            types.add(type.toString());
        }
        return types;
    }
}
