package com.android.fits;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;

import java.util.List;
import java.util.UUID;

public class GarmentPagerActivity extends AppCompatActivity {
    private static final String TAG = "GarmentPagerActivity";


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
        UUID garmentId;
        if (savedInstanceState != null){
            garmentId = (UUID) savedInstanceState.getSerializable(EXTRA_GARMENT_ID);
            Log.i(TAG, "savedInstanceState isn't null  " + garmentId.toString());
        }else {
            garmentId = (UUID) getIntent().getSerializableExtra(EXTRA_GARMENT_ID);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        //Remember that FragmentStatePagerAdapter is your agent managing the conversation
        //with ViewPager. For your agent to do its job with the fragment that getItem(int)
        //return, it needs to be able to add them to your activity. That is why it needs your FragmentManager.
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            /**
             * Uses the position argument to find the Garment and creates a
             * new fragment using that garment.
             *
             * The pager always start at zero.
             * @param position
             * @return
             */
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

        /**
         * This for loop fixes the pager starting at zero.
         */
        for (int i = 0 ; i < mGarments.size(); i ++){
            if (mGarments.get(i).getId().equals(garmentId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(EXTRA_GARMENT_ID, getIntent().getSerializableExtra(EXTRA_GARMENT_ID));
        UUID garmentId = (UUID) savedInstanceState.getSerializable(EXTRA_GARMENT_ID);
        System.out.println("savedInstanceState isn't null " + garmentId.toString());
        Log.i(TAG, "onSaveInstanceState " + garmentId.toString());
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

}
