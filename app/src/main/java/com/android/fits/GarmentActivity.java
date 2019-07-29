package com.android.fits;

import android.support.v4.app.Fragment;

public class GarmentActivity extends SingleFragmentActivity {

    /**
     * Overrided method returns a GarmentFragment.
     * @return
     */
    @Override
    protected Fragment createFragment(){
        return new GarmentFragment();
    }
}
