package com.android.fits;

import android.support.v4.app.Fragment;

public class GarmentListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new GarmentListFragment();
    }
}
