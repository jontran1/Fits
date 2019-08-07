package com.android.fits.outfits_components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.fits.Models.Outfit;
import com.android.fits.Models.OutfitLab;
import com.android.fits.R;

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
