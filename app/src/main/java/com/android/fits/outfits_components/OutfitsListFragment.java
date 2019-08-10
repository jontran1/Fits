package com.android.fits.outfits_components;

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

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;
import com.android.fits.Models.Outfit;
import com.android.fits.Models.OutfitLab;
import com.android.fits.Models.Top;
import com.android.fits.R;
import com.android.fits.TypeUtil;

import java.util.List;
import java.util.UUID;

public class OutfitsListFragment extends Fragment {

    private RecyclerView mOutfitsRecyclerView;
    private OutfitAdapter mOutfitAdapter;
    private boolean mSubtitleVisible;


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.items_list,
                container, false);

        mOutfitsRecyclerView = (RecyclerView) view.findViewById(R.id.item_list_recycler_view);

        mOutfitsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
     * OutfitHolder responsible for holding data related to outfit in the
     * recycler view list.
     */
    private class OutfitHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDate;
        private Outfit mOutfit;

        /**
         * Constructor
         * Takes in an inflater and inflates the list_item_out.xml on to the
         * recycler list.
         * @param inflater
         * @param parent
         */
        public OutfitHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_outfit, parent, false));
            mTitle = (TextView) itemView.findViewById(R.id.outfit_name);
            mDate = (TextView) itemView.findViewById(R.id.outfit_date_created);

        }

        public void bind(Outfit outfit){
            mOutfit = outfit;
            mTitle.setText(mOutfit.getOutfitName());
        }

    }

    /**
     * The OutfitAdapter knows  the details of each outfit while the
     * OutfitHolder just displays the information.
     */
    private class OutfitAdapter extends RecyclerView.Adapter<OutfitHolder>{
        private List<Outfit> mOutfits;

        /**
         * Constructor for OutfitAdapter that takes in a list of outfits.
         * @param outfits
         */
        public OutfitAdapter(List<Outfit> outfits){
            this.mOutfits = outfits;
        }

        /**
         * Creates a new OutfitHolder. After getItemCount() is called.
         * @param parent
         * @param i
         * @return
         */
        @NonNull
        @Override
        public OutfitHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new OutfitHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull OutfitHolder outfitHolder, int i) {
            Outfit outfit = mOutfits.get(i);
            outfitHolder.bind(outfit);
        }

        /**
         * Returns the size how many outfits there are so the recycler list
         * can know.
         * @return
         */
        @Override
        public int getItemCount() {
            return OutfitLab.get(getContext()).getOutfits().size();
        }
    }

    /**
     * Reloads recycler list view.
     */
    private void updateUI(){
        OutfitLab outfitLab = OutfitLab.get(getActivity());
        List<Outfit> outfits = outfitLab.getOutfits();
        mOutfitAdapter = new OutfitAdapter(outfits);
        mOutfitsRecyclerView.setAdapter(mOutfitAdapter);
    }
    /**
     * Menus are managed by callbacks from the Activity class.
     * When the menu is needed Android calls the Activity method
     * onCreateOptionsMenu(Menu), However for the fragment. It comes
     * with its own set of menu callbacks.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        /*
        Passing the res Id of the menu file. This populates the Menu
        instance with the items defined in your file.
         */
        inflater.inflate(R.menu.fragment_outfit_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else
            subtitleItem.setTitle(R.string.show_subtitle);
    }




}
