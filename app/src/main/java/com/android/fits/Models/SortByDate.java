package com.android.fits.Models;

import java.util.Comparator;

public class SortByDate implements Comparator<Garment> {
    public int compare(Garment a, Garment b){
        return b.getDate().compareTo(a.getDate());
    }
}