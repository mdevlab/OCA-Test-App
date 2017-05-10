package io.mdevlab.ocatraining.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.notification.NotificationsManager;


public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    final boolean DEFAULT_NOTIFICATIONS_STATUS = true;
    final int DEFAULT_NOTIFICATIONS_FREQUENCY = 2;
    final Set<String> DEFAULT_NOTIFICATIONS_CHAPTERS = new HashSet<>(Arrays.asList(new String[]{"0"}));

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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


    private boolean notificationsAreEnabled() {
        return preferences.getBoolean(
                getString(R.string.notifications_toggle_key),
                DEFAULT_NOTIFICATIONS_STATUS
        );
    }


    private int getNotificationsFrequency() {
        try {
            return Integer.parseInt(preferences.getString(
                    getString(R.string.notifications_frequency_key),
                    String.valueOf(DEFAULT_NOTIFICATIONS_FREQUENCY)
            ));
        } catch (NumberFormatException e) {
            return DEFAULT_NOTIFICATIONS_FREQUENCY;
        }
    }


    private Set<String> getNotificationsChapters() {
        return preferences.getStringSet(
                getString(R.string.notifications_chapters_key),
                DEFAULT_NOTIFICATIONS_CHAPTERS
        );
    }


    /**
     * Calls the notifications manager to handle turning on/off notifications
     * depending on the status chosen by the user
     */
    private void updateNotificationsStatus() {
        NotificationsManager.handleNotifications(notificationsAreEnabled(),
                getNotificationsFrequency(),
                getNotificationsChapters()
        );
    }
}
