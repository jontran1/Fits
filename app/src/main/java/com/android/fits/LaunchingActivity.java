package com.android.fits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.fits.fits_component.GarmentListActivity;
import com.android.fits.outfits_components.OutfitsListActivity;

public class LaunchingActivity extends AppCompatActivity {
    private Button launchFitsButton;
    private Button launchOutfitsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        launchFitsButton = (Button) findViewById(R.id.launch_fits);

        final Intent fitsIntent = new Intent(this, GarmentListActivity.class);
        launchFitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fitsIntent);
            }
        });

        final Intent outfitsIntent = new Intent(this, OutfitsListActivity.class);
        launchOutfitsButton = (Button) findViewById(R.id.launch_outfits);

        launchOutfitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(outfitsIntent);
            }
        });


    }
}
