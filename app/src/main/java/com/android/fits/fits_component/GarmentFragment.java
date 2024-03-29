package com.android.fits.fits_component;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;
import com.android.fits.PhotoDialogFragment;
import com.android.fits.PictureUtils;
import com.android.fits.R;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class GarmentFragment extends Fragment {

    private static final String TAG = "GarmentFragment";


    private Garment mGarment;
    private Spinner mSpinnerTypes;
    private Spinner mSpinnerSizes;
    private EditText mDescription;
    private EditText mStore;
    private EditText mBrand;
    private TextView mDate;
    private ImageView mImageView;
    private File mPhotoFile;
    private ImageButton mImageButton;

    private static final String ARG_GARMENT_ID = "garment_id";
    private List<String> mTypes;
    private List<String> mSizes;

    private static final int REQUEST_PHOTO = 2;

    // Uniquely identifies the DialogFragment in FragmentManager's list.
    private static final String DIALOG_PHOTO = "dialog_photo";



    /**
     * An activity life cycle method. Its public because it
     * will be called by whatever activity is hosting the fragment.
     * onCreate() is where you initialize your fragment.
     * @param saveInstanceState
     */
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        UUID GarmentId;
        if (saveInstanceState != null){
            GarmentId = (UUID)saveInstanceState.getSerializable(ARG_GARMENT_ID);
            Log.d(TAG,"onCreate() when saveInstanceState isn't null..." + GarmentId.toString());

        }else {
            GarmentId = (UUID)getArguments().getSerializable(ARG_GARMENT_ID);

        }
        mGarment = GarmentLab.get(getActivity()).getGarment(GarmentId);
        mTypes = mGarment.getTypes();
        mSizes = mGarment.getSizes();
        mPhotoFile = GarmentLab.get(getActivity()).getPhotoFile(mGarment);
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

        mSpinnerTypes = (Spinner)v.findViewById(R.id.garment_fragment_type);
        setSpinnerType();
        mSpinnerSizes = (Spinner)v.findViewById(R.id.garment_fragment_size);
        setSpinnerSizes();

        mDescription = (EditText)v.findViewById(R.id.garment_fragment_description);
        setDescriptionEditText();


        mDate = (TextView) v.findViewById(R.id.garment_fragment_date);
        mDate.setText("Date Create: " + mGarment.getDate().toString());
        System.out.println(mGarment.getDate().toString());

        mImageView = (ImageView)v.findViewById(R.id.garment_fragment_photo);
        mImageButton = (ImageButton)v.findViewById(R.id.garment_fragment_camera_button);
        setImageView();

        setHasOptionsMenu(true);


        return v;
    }

    /**
     * Menus are managed by callbacks from the Activity class.
     * When the menu is needed Android calls the Activity method
     * onCreateOptionsMenu(Menu), However for the fragment. It comes
     * with its own set of menu callbacks.
     *
     * This method is for creating the menu and responding to the
     * the selection of an action item.
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        /*
        Passing the res Id of the menu file. This populates the Menu
        instance with the items defined in your file.
         */
        inflater.inflate(R.menu.fragment_garment, menu);

    }

    /**
     * Handles the menu item actions.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.remove_item:
                GarmentLab.get(getActivity()).removeGarment(mGarment);
                getActivity().finish();
                return true;
            default:
                /*
                The default case calls the
                superclass implementation if the item ID
                is not in your implementation.
                 */
                return super.onOptionsItemSelected(item);
        }
    }

    private void setImageView(){
        PackageManager packageManager = getActivity().getPackageManager();

        mImageView.setImageBitmap(PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity()));

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mImageButton.setEnabled(canTakePhoto);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Uses the camera feature to take a photo of the garment.
             * Starts a hold new activity for the camera, once that activity dies
             * the result function in GarmentFragment.java is called and determines
             * what happens next.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.android.fits.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoFile.exists()){
                    FragmentManager manager = getFragmentManager();
                    DialogFragment dialogFragment = PhotoDialogFragment.newInstance(mPhotoFile.getPath());
                    dialogFragment.show(manager, DIALOG_PHOTO);
                }

            }
        });
    }

    /**
     * Set up the widget for Brand EditText and listener.
     */
    private void setBrandEditText(){
        mBrand.setText(null);
        mBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
        mStore.setText(null);
        mStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                updateGarment();

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
                updateGarment();
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
                updateGarment();

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

        GarmentLab.get(getActivity()).updateGarment(mGarment);
    }

    /**
     * Allows the parent fragment to react to the child's fragment death.
     * If the user has made a change to a crime model in the child fragment.
     * This function will allow the parent fragment to know and react accordingly.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_PHOTO){
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.android.fits.fileprovider",
                    mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updateGarment();
            updatePhotoView();
        }
    }

    /**
     * Gets the correct scale for the image by calling
     * getScaledBitmap. That is because using the full image
     * is inefficient.
     *
     * Loads the Bitmap into the image view.
     */
    private void updatePhotoView(){
        if (mPhotoFile == null || !mPhotoFile.exists()){
            mImageView.setImageDrawable(null);
        }else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity()
            );
            File imgFile = new File(GarmentLab.get(getActivity()).getPhotoFile(mGarment).getPath());
            if (imgFile.exists()){
                Bitmap img = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                mImageView.setImageBitmap(img);

            }

        }
    }

    /*
    These methods are public because the activity needs to call them.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(ARG_GARMENT_ID, mGarment.getId());
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");

    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }


}
