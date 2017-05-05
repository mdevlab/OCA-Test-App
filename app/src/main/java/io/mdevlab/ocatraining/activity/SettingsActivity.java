package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import io.mdevlab.ocatraining.R;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
