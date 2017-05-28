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
    private static final String SHARE_TYPE = "text/plain";


    public static void displayUpgradeDialog(AppCompatActivity activity, String message) {
        DialogFragment upgradeDialog = new DialogUpgrade(message);
        upgradeDialog.show(activity.getSupportFragmentManager(), DIALOG_DIALOG_TAG);
    }


    public static void displayUpgradeDialog(AppCompatActivity activity) {
        DialogFragment upgradeDialog = new DialogUpgrade(null);
        upgradeDialog.show(activity.getSupportFragmentManager(), DIALOG_DIALOG_TAG);
    }


    public static void upgrade(Context context) {
        Uri upgradeUri = Uri.parse(context.getString(R.string.upgrade_url));
        Intent upgrade = new Intent(Intent.ACTION_VIEW, upgradeUri);
        context.startActivity(upgrade);
    }


    public static void share(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(SHARE_TYPE);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_text) + context.getString(R.string.application_play_store_url));
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
    }
}
