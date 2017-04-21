package io.mdevlab.ocatestapp.dao;

import io.mdevlab.ocatestapp.model.Test;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestManager {

    private static final String TAG = TestManager.class.getSimpleName();

    public static void createTest(final Test test) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insert(test);
                    }
                });
    }

    public static Test getTestById(int testId) {
        return Realm.getDefaultInstance()
                .where(Test.class)
                .equalTo(Test.ID_COLUMN, testId)
                .findFirst();
    }

    public static RealmResults<Test> getAllTests() {
        return Realm.getDefaultInstance()
                .where(Test.class)
                .findAll();
    }

    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Test.class)
                .max(Test.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    public static void deleteAllTests() {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Test.class);
                    }
                });
    }
}
