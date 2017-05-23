package io.mdevlab.ocatraining.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.dialog.DialogUpgrade;

/**
 * Created by husaynhakeem on 5/23/17.
 */

public class UtilActions {


    private static final String DIALOG_DIALOG_TAG = "upgrade dialog";


    public static void displayUpgradeDialog(AppCompatActivity activity) {
        DialogFragment upgradeDialog = new DialogUpgrade();
        upgradeDialog.show(activity.getSupportFragmentManager(), DIALOG_DIALOG_TAG);
    }


    public static void upgrade(Context context) {
        Uri upgradeUri = Uri.parse(context.getString(R.string.upgrade_url));
        Intent upgrade = new Intent(Intent.ACTION_VIEW, upgradeUri);
        context.startActivity(upgrade);
    }
}
