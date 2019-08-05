package com.android.fits.outfits_components;

import android.support.v4.app.Fragment;

import com.android.fits.SingleFragmentActivity;

public class OutfitsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new OutfitsListFragment();
    }
}
