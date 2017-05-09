package io.mdevlab.ocatraining;

import android.app.Application;

import com.evernote.android.job.JobManager;

import io.mdevlab.ocatraining.notification.NotificationsJobCreator;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class OCA extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Job scheduler singleton initializer
        JobManager.create(this).addJobCreator(new NotificationsJobCreator());

        // Realm initialization and configuration
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("oca.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
