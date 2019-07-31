package com.android.fits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;
import com.android.fits.Models.Top;

import java.util.List;
import java.util.UUID;

public class GarmentFragment extends Fragment {
    private Garment mGarment;
    private Spinner mSpinnerTypes;
    private Spinner mSpinnerSizes;
    private TextView mDescription;

    private static final String ARG_GARMENT_ID = "garment_id";
    private List<String> mTypes;
    private List<String> mSizes;

    /**
     * An activity life cycle method. Its public because it
     * will be called by whatever activity is hosting the fragment.
     * onCreate() is where you initialize your fragment.
     * @param saveInstanceState
     */
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
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

        UUID GarmentId = (UUID)getArguments().getSerializable(ARG_GARMENT_ID);
        mGarment = GarmentLab.get(getActivity()).getGarment(GarmentId);


        mDescription = (TextView)v.findViewById(R.id.garment_fragment_description);
        mDescription.setText(mGarment.getDescription());
        setSpinners(v);

        return v;
    }

    /**
     * Set the spinners widgets and adapters.
     * @param v
     */
    public void setSpinners(View v){

        mSpinnerTypes = (Spinner)v.findViewById(R.id.garment_fragment_type);
        mSpinnerSizes = (Spinner)v.findViewById(R.id.garment_fragment_size);
        mTypes = mGarment.getTypes();
        mSizes = mGarment.getSizes();

        ArrayAdapter<String> adapterTypes = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, mTypes);
        mSpinnerTypes.setAdapter(adapterTypes);
        ArrayAdapter<String> adapterSizes = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, mSizes);
        mSpinnerSizes.setAdapter(adapterSizes);
    }
}
