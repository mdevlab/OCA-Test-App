package io.mdevlab.ocatraining.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * Created by bachiri on 5/29/17.
 */


public class AnalyticsManager {

    private FirebaseAnalytics mFirebaseAnalytics;
    private Context mContext;
    private static AnalyticsManager instance = null;

    /**
     * private constructor for the Singleton Instance
     *
     * @param context
     */
    private AnalyticsManager(Context context) {
        mContext = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }

    /**
     * initialize the Global analytics instance
     *
     * @param context
     * @return
     */
    public static AnalyticsManager initialize(Context context) {
        if (instance == null) {
            instance = new AnalyticsManager(context);
        }
        return instance;
    }

    /**
     * Get the AnalyticsManager from anywhere within the app
     *
     * @return
     */
    public static AnalyticsManager getInstance() {
        return instance;
    }

    /**
     * Track the given event
     * @param eventName event name
     * @param bundle additional properties attached to the event
     */
    public void logEvent(String eventName, Bundle bundle) {
        mFirebaseAnalytics.logEvent(eventName, bundle);
    }

}
