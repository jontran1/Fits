package com.android.fits;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;

import java.util.List;
import java.util.UUID;

public class GarmentPagerActivity extends AppCompatActivity {

    private static final String EXTRA_GARMENT_ID = "com.android.fits.garment_id";
    private ViewPager mViewPager;
    private List<Garment> mGarments;

    /**
     * Creates a new intent for GarmentPagerActivity.
     * @param context
     * @param garmentID
     * @return
     */
    public static Intent newIntent(Context context, UUID garmentID){
        Intent intent = new Intent(context, GarmentPagerActivity.class);
        intent.putExtra(EXTRA_GARMENT_ID, garmentID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garment_pager);

        mGarments = GarmentLab.get(this).getGarments();

        mViewPager = (ViewPager) findViewById(R.id.garment_view_paper);
        UUID garmentId = (UUID) getIntent().getSerializableExtra(EXTRA_GARMENT_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Garment garment = mGarments.get(position);
                return GarmentFragment.newInstance(garment.getId());
            }

            @Override
            public int getCount() {
                return mGarments.size();
            }
        });

    }


}
