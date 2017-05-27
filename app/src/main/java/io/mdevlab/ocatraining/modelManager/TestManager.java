package io.mdevlab.ocatraining.modelManager;

import io.mdevlab.ocatraining.model.Test;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestManager {

    /**
     * @param test Test object to be inserted
     */
    public static void createTest(final Test test) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insert(test);
                    }
                });
    }

    /**
     * @return List of all tests
     */
    public static RealmResults<Test> getAllTests() {
        return Realm.getDefaultInstance()
                .where(Test.class)
                .findAll();
    }

    /**
     * @return Highest index in the test table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Test.class)
                .max(Test.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    /**
     * Delete all tests
     */
    public static void deleteAllTests() {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Test.class);
                    }
                });
    }

    /**
     * Get the last saved test
     *
     * @param type FINAL_TEST_MODE or CUSTOM_TEST_MODE
     * @return
     */
    public static Test getLastTest(int type) {
        return Realm.getDefaultInstance()
                .where(Test.class)
                .equalTo("type", type)
                .findFirst();

    }


    public static void cleanOldTest(final int finalTestMode) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        RealmResults<Test> result = realm.where(Test.class).equalTo("type", finalTestMode).findAll();
                        result.deleteAllFromRealm();
                    }
                });
    }
}
