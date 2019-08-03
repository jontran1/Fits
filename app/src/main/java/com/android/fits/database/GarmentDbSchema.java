package com.android.fits.database;

public class GarmentDbSchema {
    public static final class GarmentTable{
        /*
        The CrimeTable class only exists to define the String
        constants needed to describe the moving pieces of your table
        definition. The first piece of that definition is the name of the
        table in your database, CrimeTable.NAME.
         */
        public static final String NAME = "garments";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String SIZE = "size";
            public static final String TYPE = "type";
            public static final String GARMENT_TYPE = "garment_type";
        }
    }
}
