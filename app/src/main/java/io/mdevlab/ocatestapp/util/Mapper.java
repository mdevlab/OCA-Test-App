package io.mdevlab.ocatestapp.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class Mapper <E extends RealmModel> {

    private static Mapper mapper;

    public static Mapper instance() {
        if (mapper == null)
            mapper = new Mapper();
        return mapper;
    }

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
