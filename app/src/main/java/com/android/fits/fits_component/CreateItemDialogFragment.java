package com.android.fits.fits_component;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.android.fits.Models.Garment;

import com.android.fits.R;
import com.android.fits.TypeUtil.Type;

public class CreateItemDialogFragment extends DialogFragment {

    public static final String EXTRA_TYPE = "com.android.fits.new_item_dialog_id";
    private Type mType;
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
                    mType = Type.Hats;
                    dismiss();
                    sendResult(Activity.RESULT_OK, mType);
                }else if (mRadioTop.isChecked()){
                    mType = Type.Top;
                    dismiss();
                    sendResult(Activity.RESULT_OK, mType);

                }else if (mRadioPants.isChecked()){
                    mType = Type.Pants;
                    dismiss();
                    sendResult(Activity.RESULT_OK, mType);

                }else if (mRadioShoes.isChecked()){
                    mType = Type.Shoes;
                    dismiss();
                    sendResult(Activity.RESULT_OK, mType);

                }else {
                    // If the user doesn't select an option.
                    dismiss();
                    sendResult(Activity.RESULT_CANCELED);
                }

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.new_item_dialog_title)
                .create();
    }

    /**
     * Sets the result extra with the newly created garmentId.
     * Then this functions calls the parent fragment's onActivityResult function.
     * @param resultCode
     * @param type
     */
    private void sendResult(int resultCode, Type type){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TYPE, type);

        /**
         * getTargetRequestCode() is to set the parent's request code. So the parent
         * knows that this fragment is their child.
         * ResultCode is RESULT_OK to show the parent it was done correctly.
         * Intent contains information for the parent.
         */
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void sendResult(int resultCode){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();

        /**
         * getTargetRequestCode() is to set the parent's request code. So the parent
         * knows that this fragment is their child.
         * ResultCode is RESULT_OK to show the parent it was done correctly.
         * Intent contains information for the parent.
         */
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }



}
