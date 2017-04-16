package io.mdevlab.ocatestapp.dao;

import android.content.Context;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class RealmManager {

    private static RealmManager instance;
    private Context context;

    private RealmManager(Context context) {
        this.context = context;
    }

    public static RealmManager with(Context context) {
        if (instance == null)
            instance = new RealmManager(context);
        return instance;
    }
}
