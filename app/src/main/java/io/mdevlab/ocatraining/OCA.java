package io.mdevlab.ocatraining;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.evernote.android.job.JobManager;

import io.fabric.sdk.android.Fabric;
import io.mdevlab.ocatraining.notification.NotificationsJobCreator;
import io.mdevlab.ocatraining.notification.NotificationsManager;
import io.mdevlab.ocatraining.util.UtilSharedPreferences;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class OCA extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        setUpJobManager();
        setUpRealm();
        setUpNotifications();
        setUpCrashlytics();
    }


    private void setUpJobManager() {
        JobManager.create(this).addJobCreator(new NotificationsJobCreator());
    }


    private void setUpRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("oca.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }


    private void setUpNotifications() {
        if (UtilSharedPreferences.with(getApplicationContext()).isFirstAppLaunch()) {
            NotificationsManager.handleNotifications(getApplicationContext());
            UtilSharedPreferences.with(getApplicationContext()).setAppHasBeenLaunched();
        }
    }


    private void setUpCrashlytics() {
        Fabric.with(this, new Crashlytics());
    }
}
