package io.mdevlab.ocatestapp.app;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class OCA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
