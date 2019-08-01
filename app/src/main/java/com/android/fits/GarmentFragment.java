package com.android.fits;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;

import java.util.List;
import java.util.UUID;

public class GarmentFragment extends Fragment {
    private Garment mGarment;
    private Spinner mSpinnerTypes;
    private Spinner mSpinnerSizes;
    private EditText mDescription;
    private EditText mStore;
    private EditText mBrand;

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
        mTypes = mGarment.getTypes();
        mSizes = mGarment.getSizes();
        mSpinnerTypes = (Spinner)v.findViewById(R.id.garment_fragment_type);
        setSpinnerType();
        mSpinnerSizes = (Spinner)v.findViewById(R.id.garment_fragment_size);
        setSpinnerSizes();

        mDescription = (EditText)v.findViewById(R.id.garment_fragment_description);
        setDescriptionEditText();

        mStore = (EditText) v.findViewById(R.id.garment_fragment_store);
        setStoreEditText();

        mBrand = (EditText) v.findViewById(R.id.garment_fragment_brand);
        setBrandEditText();

        return v;
    }

    /**
     * Set up the widget for Brand EditText and listener.
     */
    private void setBrandEditText(){
        mBrand.setText(mGarment.getBrand());
        mBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGarment.setBrand(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Set up the widget for Store EditText and listener.
     */
    private void setStoreEditText(){
        mStore.setText(mGarment.getStore());
        mStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGarment.setStore(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Sets up the Description EditText widget, including the listener.
     */
    private void setDescriptionEditText(){
        mDescription.setText(mGarment.getDescription());
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGarment.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Set the spinners Type widgets and adapters.
     * Sets listeners.
     */
    private void setSpinnerType(){


        final ArrayAdapter<String> adapterTypes = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, mTypes);
        mSpinnerTypes.setAdapter(adapterTypes);

        mSpinnerTypes.setSelection(mTypes.indexOf(mGarment.getType()));
        mSpinnerTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGarment.setType(mTypes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * Set the spinners Sizes widgets and adapters.
     * Sets listeners.
     */
    private void setSpinnerSizes(){
        ArrayAdapter<String> adapterSizes = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, mSizes);
        mSpinnerSizes.setAdapter(adapterSizes);
        mSpinnerSizes.setSelection(mSizes.indexOf(mGarment.getSize()));

        mSpinnerSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGarment.setSize(mSizes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Update garment model.
     */
    private void updateGarment(){

    }
}
