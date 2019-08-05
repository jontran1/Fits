package com.android.fits.fits_component;

import android.support.v4.app.Fragment;

import com.android.fits.SingleFragmentActivity;

public class GarmentListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new GarmentListFragment();
    }
}
