package io.mdevlab.ocatraining.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class Mapper<E extends RealmModel> {

    // Unique instance of this class
    private static Mapper mapper;

    /**
     * Method that returns the unique instance of this class
     * Also instantiates it if needed
     *
     * @return
     */
    public static Mapper instance() {
        if (mapper == null)
            mapper = new Mapper();
        return mapper;
    }

    /**
     * Generic method that transforms and json input from a file to data in a realm table
     *
     * @param context  Context of component calling this method
     * @param fileName Name of file containing the json data
     * @param clazz    Name of the RealmModel class the json input will be put in
     */
    public void fromJsonToRealm(final Context context, final String fileName, final Class<E> clazz) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        try {
                            InputStream is = context.getAssets().open(fileName);
                            realm.createAllFromJson(clazz, is);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }
}
