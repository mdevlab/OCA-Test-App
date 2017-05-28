package io.mdevlab.ocatraining.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.notification.NotificationsManager;


public class SettingsActivity extends ActivityBase implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUpToolbar(getString(R.string.title_activity_settings));
        setUpDFP(null);

        preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        updateNotificationsStatus();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }


    /**
     * Calls the notifications manager to handle turning on/off notifications
     * depending on the status chosen by the user
     */
    private void updateNotificationsStatus() {
        NotificationsManager.handleNotifications(SettingsActivity.this);
    }
}
