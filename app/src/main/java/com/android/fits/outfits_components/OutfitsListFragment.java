package com.android.fits.outfits_components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;
import com.android.fits.Models.Outfit;
import com.android.fits.Models.OutfitLab;
import com.android.fits.R;
import com.android.fits.TypeUtil;

import java.util.List;

public class OutfitsListFragment extends Fragment {

    private RecyclerView mOutfitsRecyclerView;

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

        Outfit outfit = new Outfit("Jon's Big Day");
        System.out.println(outfit.getOutfitName());
        System.out.println(outfit.getUUID());
        OutfitLab.get(getActivity()).addOutfit(outfit);
        List list = OutfitLab.get(getActivity()).getOutfits();

        Garment top = Garment.createNewGarment(TypeUtil.Type.Top);
        Garment pants = Garment.createNewGarment(TypeUtil.Type.Pants);
        Garment hat = Garment.createNewGarment(TypeUtil.Type.Hats);
        GarmentLab.get(getActivity()).addGarment(top);
        GarmentLab.get(getActivity()).addGarment(pants);
        GarmentLab.get(getActivity()).addGarment(hat);


        OutfitLab.get(getActivity()).addGarmentsToOutfits(outfit.getUUID(), top.getId());
        OutfitLab.get(getActivity()).addGarmentsToOutfits(outfit.getUUID(), pants.getId());
        OutfitLab.get(getActivity()).addGarmentsToOutfits(outfit.getUUID(), hat.getId());

        List<Garment> garments_related_outfit = OutfitLab.get(getActivity()).getGarmentsRelatedOutfit(outfit.getUUID(), getActivity());
        System.out.println(garments_related_outfit.toString());
        System.out.println(list.toString());

        return view;
    }

    private class OutfitHolder extends RecyclerView.ViewHolder {

        public OutfitHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_outfit, parent, false));

        }

    }

    private class OutfitAdapter extends RecyclerView.Adapter<OutfitHolder>{
        public OutfitAdapter(){

        }

        @NonNull
        @Override
        public OutfitHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull OutfitHolder outfitHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}
