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
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;

import java.io.File;
import java.util.List;

import com.android.fits.PictureUtils;
import com.android.fits.R;
import com.android.fits.TypeUtil.Type;


public class GarmentListFragment extends Fragment {
    private static final String TAG = "GarmentListFragment";
    private RecyclerView mGarmentRecyclerView;
    private GarmentAdapter mGarmentAdapter;
    private boolean mSubtitleVisible;

    // Uniquely identifies the DialogFragment in FragmentManager's list.
    private static final String DIALOG_NEW_ITEM = "NewItemDialog";
    private static final String NEW_ITEM_PHOTO = "Newly_Created_Garment_Photo";
    private File mNewLyCreatedPhotoFile;

    private static final int REQUEST_NEW_ITEM = 1;
    private static final int REQUEST_PHOTO = 2;

    /**
     * Initizes the fragment.
     * @param saveInstanceState
     */
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        if (saveInstanceState != null){
            mNewLyCreatedPhotoFile = new File(saveInstanceState.getString(NEW_ITEM_PHOTO));
        }
    }
    /**
     * Sets up the view for GarmentListFragment.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.items_list,
                container, false);

        mGarmentRecyclerView = (RecyclerView) view.
                findViewById(R.id.item_list_recycler_view);


        /*
        Note that as soon you create your recyclerView you give it another
        object called a LayoutManager. RecyclerView requires a LayoutManager
        to work. If you forget to give it one it will crash.
        The RecyclerView does not position items on the screen itself it
        delegates that job to the LayoutManager. The LayoutManager positions
        every item and also defines how scrolling works.
         */
        mGarmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        /*
        The fragment manager is responsible for calling
        Fragment.onCreateOptionsMen(...) when the activity receives its
        onCreateOptionsMenu(...) callback from the OS. You must
        explicitly tell the FragmentManager that your fragment should
        receive a call to onCreateOptionsMenu(...).
         */
        setHasOptionsMenu(true);
        return view;

    }


    /**
     * Steps
     * Recycler view calls getItemCount()
     * Adapter returns count\
     * Recycler view calls onCreateViewHolder()
     * Adapter returns ViewHolder
     * Recycler view calls onBindViewHolder()
     * Adapter binds the data to the ViewHolder.
     */
    private class GarmentHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener{

        private TextView mTypeView;
        private TextView mSizeView;
        private TextView mDateView;
        private ImageView mImageView;
        private Garment mGarment;

        /**
        * This constructor inflates list_item_garment.xml Immediately you pass it into
        * super(...), ViewHolder's constructor. The base ViewHolder class will then
        * hold on to the items_list.xml view.
        * RecyclerView just shows the items. The adapter has the information
        * on the models and lists. When recyclerview needs to display a new ViewHolder or
        * connect a garment object to an existing viewholder it will ask the adapter for
        * help by calling a method on it.
        * NOTE MAIN PURPOSE is for setting up widgets. The bind functions binds the model with view.
        *
        * @param inflater
        * @param parent
        */
        public GarmentHolder(LayoutInflater inflater, ViewGroup parent){
            // this is inflating the list_item_garment.xml on to item_list.xml.
            super(inflater.inflate(R.layout.list_item_garment, parent, false));
            itemView.setOnClickListener(this);
            mTypeView = (TextView) itemView.findViewById(R.id.garment_list_item_type);
            mSizeView = (TextView) itemView.findViewById(R.id.garment_list_item_size);
            mDateView = (TextView) itemView.findViewById(R.id.garment_list_item_date);
            mImageView = (ImageView) itemView.findViewById(R.id.garment_list_item_image);
        }

        @Override
        public void onClick(View view){
            Intent intent = GarmentPagerActivity.newIntent(getActivity(), mGarment.getId());
            startActivity(intent);
        }

        /**
         * Binds the data to the view holder.
         * @param garment
         */
        public void bind(Garment garment){
            mGarment = garment;
            mTypeView.setText(mGarment.getType());
            mSizeView.setText(getString(R.string.list_item_size) + mGarment.getSize());
            mDateView.setText(getString(R.string.list_item_date_created) + mGarment.getDate().toString());
            File imgFile = new File(GarmentLab.get(getActivity()).getPhotoFile(mGarment).getPath());
            if (imgFile.exists()){
                Bitmap img = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                mImageView.setImageBitmap(PictureUtils.getScaledBitmap(imgFile.getPath(),getActivity()));
                mImageView.setImageBitmap(img);

            }
        }

    }

    /**
     * RecyclerView just shows the items. The adapter has the information
     * on the models and lists. When recyclerview needs to display a new ViewHolder or
     * connect a garment object to an existing viewholder it will ask the adapter for
     * help by calling a method on it.
     */
    private class GarmentAdapter extends RecyclerView.Adapter<GarmentHolder>{
        private List<Garment> mGarments;

        /**
         * Gets the list for the Recycler view.
         * @param garments
         */
        public GarmentAdapter(List<Garment> garments){
            mGarments = garments;
        }

        /**
         * Creates the ViewHolder for the garment object. Uses the Layout from
         * the main activity host. Which is the items_list.xml.
         * @param parent
         * @param i
         * @return GarmentHolder
         */
        @NonNull
        @Override
        public GarmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            // The layout is fragment_garment_list.xml
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GarmentHolder(layoutInflater, parent);
        }

        /**
         * Is called by the Recycler view. It calls the bind function in
         * Recycler view.
         * Binds the data to the view holder.
         * @param garmentHolder
         * @param i
         */
        @Override
        public void onBindViewHolder(@NonNull GarmentHolder garmentHolder, int i) {
            Garment garment = mGarments.get(i);
            garmentHolder.bind(garment);
        }

        @Override
        public int getItemCount() {
            return mGarments.size();
        }
    }




    /**
     * Updates the garment list fragment.
     */
    private void updateUI(){
        GarmentLab garmentLab = GarmentLab.get(getActivity());
        List<Garment> garments = garmentLab.getGarments_OrderedBy_RecentDate();

        mGarmentAdapter = new GarmentAdapter(garments);
        mGarmentRecyclerView.setAdapter(mGarmentAdapter);

        updateSubtitle();
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
        inflater.inflate(R.menu.fragment_garment_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else
            subtitleItem.setTitle(R.string.show_subtitle);

    }

    /**
     * Handles the menu item actions.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_item:
                startItemCreationDialog();
                //Returns true to indicate no further processing is needed.
                return true;

            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                // Menu has been chanced so it should be recreated by invalidateOptionsMenu()
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
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

    /**
     * Displays the number of items in recycler list in action bar.
     */
    private void updateSubtitle(){
        GarmentLab garmentLab = GarmentLab.get(getActivity());
        int garmentCount = garmentLab.getSize();
        String subtitle = getString(R.string.subtitle_format, garmentCount);
        if (!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * Starts the new item dialog fragment.
     */
    private void startItemCreationDialog(){
        FragmentManager manager = getFragmentManager();
        CreateItemDialogFragment dialog = new CreateItemDialogFragment();
        dialog.setTargetFragment(GarmentListFragment.this, REQUEST_NEW_ITEM);
        dialog.show(manager, DIALOG_NEW_ITEM);
    }

    /**
     * Starts up the camera app. Once the garment is created it is added to the list.
     */
    public void startCamera(Garment garment){
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mNewLyCreatedPhotoFile = GarmentLab.get(getActivity()).getPhotoFile(garment);


        Uri uri = FileProvider.getUriForFile(getActivity(),"com.android.fits.fileprovider", mNewLyCreatedPhotoFile);

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
    /**
     * Allows the parent fragment to react to the child's fragment death.
     * This function will allow the parent fragment to know and react accordingly.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // If the result was unsuccessful then return
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_PHOTO){

            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.android.fits.fileprovider",
                    mNewLyCreatedPhotoFile);

            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            Garment newlyCreatedGarment =  mGarmentAdapter.mGarments.get(0);
            Intent intent = GarmentPagerActivity.newIntent(getActivity(), newlyCreatedGarment.getId());
            startActivity(intent);



        }else if (requestCode == REQUEST_NEW_ITEM){
            Type type = (Type) data.getSerializableExtra(CreateItemDialogFragment.EXTRA_TYPE);
            Garment newGarment = GarmentLab.createNewGarment(type);
            mNewLyCreatedPhotoFile = GarmentLab.get(getActivity()).getPhotoFile(newGarment);
            updateUI();
            startCamera(newGarment);
        }
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        /**
         * When the user opens the GarmentFragment this Fragment will call onSaveInstanceState()
         * but there will be no newlyCreatePhoto. This if statement will check for that.
         */
        if (mNewLyCreatedPhotoFile != null){
            savedInstanceState.putSerializable(NEW_ITEM_PHOTO, mNewLyCreatedPhotoFile.toString());
        }
        Log.i(TAG, "onSaveInstanceState() called");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    /**
     * The garment fragment is pushed on the stack but
     * once the user backs out and that fragment is popped
     * off the stack. The fragment list needs to update
     * whatever changes the user made to the model.
     */
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
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
