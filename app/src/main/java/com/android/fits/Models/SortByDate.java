package com.android.fits.Models;

import java.util.Comparator;

public class SortByDate implements Comparator<Garment> {
    /**
     * Used by garment to be sorted by date by collections.sort function.
     * @param a
     * @param b
     * @return
     */
    public int compare(Garment a, Garment b){
        return b.getDate().compareTo(a.getDate());
    }
}