package com.android.fits;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class PhotoDialogFragment extends DialogFragment {

    private static final String ARG_FILE_PATH = "com.android.fits.photo";
    private ImageView mImageView;

    public static PhotoDialogFragment newInstance(String filePath){
        Bundle args = new Bundle();
        args.putString(ARG_FILE_PATH, filePath);

        PhotoDialogFragment fragment = new PhotoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.photo_dialog, null);
        mImageView = (ImageView) v.findViewById(R.id.garment_dialog_fragment_photo);
        String photoPath = getArguments().getString(ARG_FILE_PATH);
        Bitmap bitmap = PictureUtils.getScaledBitmap(photoPath, getActivity());
        mImageView.setImageBitmap(bitmap);
        System.out.println(photoPath);

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(v);
        return dialog;
    }

}
