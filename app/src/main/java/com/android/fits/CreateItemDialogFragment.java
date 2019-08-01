package com.android.fits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.fits.Models.Garment;
import com.android.fits.Models.GarmentLab;
import com.android.fits.Models.Pants;
import com.android.fits.Models.Shoe;
import com.android.fits.Models.Top;

import java.util.UUID;

public class CreateItemDialogFragment extends DialogFragment {

    public static final String EXTRA_ID = "com.android.fits.new_item_dialog_id";
    private Button mDialogOkButton;
    private Garment mGarment;
    RadioButton mRadioHat, mRadioTop, mRadioPants, mRadioShoes;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_item, null);

        mDialogOkButton = v.findViewById(R.id.dialog_new_item_ok);
        mRadioHat = v.findViewById(R.id.radio_hats);
        mRadioTop = v.findViewById(R.id.radio_tops);
        mRadioPants = v.findViewById(R.id.radio_pants);
        mRadioShoes = v.findViewById(R.id.radio_shoes);

        mDialogOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRadioHat.isChecked()){
                    Toast.makeText(getActivity(),"r1 clicked", Toast.LENGTH_SHORT).show();
                }else if (mRadioTop.isChecked()){
                     mGarment = new Top();
                }else if (mRadioPants.isChecked()){
                     mGarment = new Pants();
                }else if (mRadioShoes.isChecked()){
                    mGarment = new Shoe();
                }
                GarmentLab.get(getActivity()).addGarment(mGarment);
                dismiss();
                sendResult(Activity.RESULT_OK, mGarment.getId());
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Select Item Type:")
                .create();
    }

    /**
     * Sets the result extra with the newly created garmentId.
     * Then this functions calls the parent fragment's onActivityResult function.
     * @param resultCode
     * @param garmentId
     */
    private void sendResult(int resultCode, UUID garmentId){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, garmentId);

        /**
         * getTargetRequestCode() is to set the parent's request code. So the parent
         * knows that this fragment is their child.
         * ResultCode is RESULT_OK to show the parent it was done correctly.
         * Intent contains information for the parent.
         */
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }



}
