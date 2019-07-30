package com.android.fits;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class GarmentActivity extends SingleFragmentActivity {

    private static final String EXTRA_GARMENT_ID = "com.android.fits.garment_id";

    /**
     * Returns an intent with the extra of garmentId.
     * @param context
     * @param garmentId
     * @return
     */
    public static Intent newIntent(Context context, UUID garmentId){
        Intent intent = new Intent(context, GarmentActivity.class);
        intent.putExtra(EXTRA_GARMENT_ID, garmentId);
        return intent;
    }

    /**
     * Overrided method returns a GarmentFragment.
     * @return
     */
    @Override
    protected Fragment createFragment(){
        UUID garmentId = (UUID) getIntent().getSerializableExtra(EXTRA_GARMENT_ID);
        return GarmentFragment.newInstance(garmentId);
    }
}
