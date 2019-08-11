package com.android.fits;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterTextDialogFragment extends DialogFragment {
    private static final String STRING_NAME_OF_OUTFIT = "com.android.fits.name";
    private EditText mEditText;
    private Button mOkButton;
    /**
     * Returns the string name of outfit. The function is here because
     * the hosting parent doesn't need to know the implementation details
     * of the dialog fragment.
     * @param intent
     * @return
     */
    public static String getStringNameOfOutfit(Intent intent){
        return intent.getStringExtra(STRING_NAME_OF_OUTFIT);
    }

    /**
     * Sets up the dialog fragment.
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Getting the layout from the hosting activity so it can
        // inflate the dialog on screen.
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.outfit_name_dialog, null);
        mEditText = (EditText)v.findViewById(R.id.edit_text_name_of_outfit);
        mOkButton = (Button)v.findViewById(R.id.dialog_new_outfit_ok);

        /**
         * Calls sendResult() and closes the dialog fragment.
         */
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult(Activity.RESULT_OK, mEditText.getText().toString());
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.enter_name)
                .create();
    }

    /**
     * Places data inside the intent for the hosting parent
     * and calls the hosting parent's onActivityResult function.
     * @param resultCode
     * @param outfit_name
     */
    private void sendResult(int resultCode, String outfit_name){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(STRING_NAME_OF_OUTFIT, outfit_name);
        /**
         * RequestCode is set by the hosting activity so it can tell its
         * children apart.
         * ResultCode is to let the parent know what happened to the child and
         * how to react.
         * Intent contains the data the hosting parent needs. Which in this case
         * is a fragment.
         */
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
