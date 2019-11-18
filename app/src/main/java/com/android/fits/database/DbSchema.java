package com.android.fits.database;

public class DbSchema {
    public static final class GarmentTable{
        /*
        The CrimeTable class only exists to define the String
        constants needed to describe the moving pieces of your table
        definition. The first piece of that definition is the name of the
        table in your database, CrimeTable.NAME.
         */
        public static final String NAME = "garments";

        public static final class Cols{
            public static final String UUID = "garment_uuid";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date_created";
            public static final String SIZE = "size";
            public static final String TYPE = "type";
            public static final String GARMENT_TYPE = "garment_type";
        }
    }

    /**
     * This is the representation of what the outfit table looks like.
     * Table Name: outfits
     * UUID is outfit ID
     * OUTFIT_NAME is name of outfit
     * DATE is the date the outfit was created.
     */
    public static final class OutfitTable{
        public static final String NAME = "outfits";

        public static final class Cols{
            public static final String UUID = "outfit_uuid";
            public static final String OUTFIT_NAME = "outfit_name";
            public static final String DATE = "date_created";
        }
    }

    /**
     * Representation of the garments related to the outfit.
     * Each outfit UUID has a garment ID.
     */
    public static final class Outfit_Garment_Relation{
        public static final String NAME = "outfits_garments_relations";

        public static final class Cols{
            public static final String UUID = "outfit_uuid";
            public static final String GARMENT_UUID = "garment_id";
        }
    }
}
