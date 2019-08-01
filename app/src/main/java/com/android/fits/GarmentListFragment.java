package com.android.fits;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import java.util.List;

public class GarmentListFragment extends Fragment {
    private RecyclerView mGarmentRecyclerView;
    private GarmentAdapter mGarmentAdapter;

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
         *
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
}
