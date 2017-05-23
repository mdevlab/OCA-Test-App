package io.mdevlab.ocatraining.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.util.UtilActions;

/**
 * Created by husaynhakeem on 5/23/17.
 */

public class DialogUpgrade extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_upgrade, null));

        builder.setPositiveButton(R.string.upgrade_dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UtilActions.upgrade(DialogUpgrade.this.getActivity());
            }
        });

        builder.setNegativeButton(R.string.upgrade_dialog_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogUpgrade.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
