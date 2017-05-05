package io.mdevlab.ocatraining.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class OCA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Realm initialization and configuration
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("oca.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}