package com.android.fits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;

import java.util.UUID;

public class GarmentFragment extends Fragment {
    private Garment mGarment;
    private Spinner mSpinnerGarmentType;
    private static final String ARG_GARMENT_ID = "garment_id";

    /**
     * An activity life cycle method. Its public because it
     * will be called by whatever activity is hosting the fragment.
     * onCreate() is where you initialize your fragment.
     * @param saveInstanceState
     */
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        mGarment = new Garment();
        mGarment.setBrand("Wall-Mart");
        mGarment.setColor("Black");
        mGarment.setSize("M");
        mGarment.setDescription("Black Jacket with blue buttons");
    }

    /**
     * Creates a new fragment and set the arguments to that fragment.
     * @param garmentId
     * @return
     */
    public static GarmentFragment newInstance(UUID garmentId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_GARMENT_ID, garmentId);

        GarmentFragment fragment = new GarmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This is where configuration for the fragment will take place.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_garment, container, false);

        mSpinnerGarmentType = (Spinner)v.findViewById(R.id.garment_fragment_type);



        TextView textView = (TextView)v.findViewById(R.id.garment_fragment_description);
        Garment garment = GarmentLab.get(getActivity()).getGarment((UUID)getArguments().getSerializable(ARG_GARMENT_ID));
        textView.setText(garment.getDescription());

        return v;
    }
}
