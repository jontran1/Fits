package com.android.fits;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private class GarmentHolder extends RecyclerView.ViewHolder{

        private TextView mDescriptionView;
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
        * @param inflater
        * @param parent
        */
        public GarmentHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_garment, parent, false));
            mDescriptionView = (TextView) itemView.findViewById(R.id.garment_fragment_description);
            mSizeView = (TextView) itemView.findViewById(R.id.garment_fragment_size);
            mDateView = (TextView) itemView.findViewById(R.id.garment_fragment_date);


        }

        public void bind(Garment garment){
            mGarment = garment;
            mDescriptionView.setText(mGarment.getDescription());
            mSizeView.setText(mGarment.getSize());
            mDateView.setText(mGarment.getDate().toString());
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
}
