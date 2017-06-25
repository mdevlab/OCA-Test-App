package io.mdevlab.ocatraining.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.analytics.AnalyticsManager;
import io.mdevlab.ocatraining.util.UtilActions;

/**
 * Created by husaynhakeem on 5/23/17.
 */

public class DialogUpgrade extends DialogFragment {


    public static final String MESSAGE_KEY = "message-key";


    public DialogUpgrade() {
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_upgrade, null);
        builder.setView(dialogView);

        String message = "";
        if (getArguments() != null && getArguments().containsKey(MESSAGE_KEY))
            message = getArguments().getString(MESSAGE_KEY);

        ((TextView) dialogView.findViewById(R.id.upgrade_dialog_message)).setText(message);

        builder.setPositiveButton(R.string.upgrade_dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UtilActions.upgrade(DialogUpgrade.this.getActivity());
                //Firebase Analytics Tracking
                upgradeEventTracking();
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

    private void upgradeEventTracking() {
        Bundle bundle = new Bundle();
        bundle.putString(getResources().getString(R.string.property_name_source), getResources().getString(R.string.attribute_value_home));
        AnalyticsManager.getInstance().logEvent(getContext().getString(R.string.event_click_upgrade), bundle);
    }
}
