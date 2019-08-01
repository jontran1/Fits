package com.android.fits;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class GarmentListFragment extends Fragment {
    private RecyclerView mGarmentRecyclerView;
    private GarmentAdapter mGarmentAdapter;

    // Uniquely identifies the DialogFragment in FragmentManager's list.
    private static final String DIALOG_NEW_ITEM = "NewItemDialog";

    private static final int REQUEST_PHOTO = 0;
    private static final int REQUEST_NEW_ITEM = 1;
    private File mPhotoFile;

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

        View view = inflater.inflate(R.layout.fragment_garment_list,
                container, false);

        mGarmentRecyclerView = (RecyclerView) view.
                findViewById(R.id.garment_recycler_view);

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
        private Garment mGarment;

        /**
        * This constructor inflates lisT_item_garment.xml Immediately you pass it into
        * super(...), ViewHolder's constructor. The base ViewHolder class will then
        * hold on to the fragment_garment_list.xml view.
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
            super(inflater.inflate(R.layout.list_item_garment, parent, false));
            itemView.setOnClickListener(this);
            mTypeView = (TextView) itemView.findViewById(R.id.garment_list_item_type);
            mSizeView = (TextView) itemView.findViewById(R.id.garment_list_item_size);
            mDateView = (TextView) itemView.findViewById(R.id.garment_list_item_date);
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
            mSizeView.setText("Size: " + mGarment.getSize());
            mDateView.setText("Date Created: " + mGarment.getDate().toString());
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
         * the main activity host. Which is the fragment_garment_list.xml.
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
            Garment garment = mGarments.get(mGarments.size()-i-1);
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
        System.out.println("Recycler List was updated....");
        GarmentLab garmentLab = GarmentLab.get(getActivity());
        List<Garment> garments = garmentLab.getGarments();

        mGarmentAdapter = new GarmentAdapter(garments);
        mGarmentRecyclerView.setAdapter(mGarmentAdapter);
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
                Toast.makeText(getActivity(),"new item was clicked",Toast.LENGTH_SHORT).show();
                startItemCreationDialog();

                //Returns true to indicate no further processing is needed.
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

    private void startItemCreationDialog(){
        FragmentManager manager = getFragmentManager();
        CreateItemDialogFragment dialog = new CreateItemDialogFragment();
        dialog.setTargetFragment(GarmentListFragment.this, REQUEST_NEW_ITEM);
        dialog.show(manager, DIALOG_NEW_ITEM);
    }

    /**
     * Starts up the camera app.
     */
    private void startCamera(Garment garment){
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mPhotoFile = GarmentLab.get(getActivity()).getPhotoFile(garment);

        Uri uri = FileProvider.getUriForFile(getActivity(),"com.android.fits.fileprovider", mPhotoFile);

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
            updateUI();
            Intent intent = GarmentActivity.newIntent(getActivity(), mGarmentAdapter.mGarments.get(mGarmentAdapter.mGarments.size()-1).getId());
            startActivity(intent);
        }else if (requestCode == REQUEST_NEW_ITEM){
            UUID garmentId = (UUID)data.getSerializableExtra(CreateItemDialogFragment.EXTRA_TYPE);
            startCamera(GarmentLab.get(getActivity()).getGarment(garmentId));
        }
    }

}
