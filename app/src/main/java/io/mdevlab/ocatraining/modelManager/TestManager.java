package io.mdevlab.ocatraining.modelManager;

import android.support.annotation.Nullable;

import io.mdevlab.ocatraining.model.Test;
import io.realm.Realm;
import io.realm.RealmResults;

import static io.realm.Realm.getDefaultInstance;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestManager {

    /**
     * @param test Test object to be inserted
     */
    public static Test createTest(final Test test) {

        /*
        I used copyToRealm instead of the realm.executeTransaction cause i need to instance of the created object
          the object will be updated and persisted on any modification
         */
        Test createdTest;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        createdTest = realm.copyToRealm(test);
        realm.commitTransaction();
        realm.close();
        return createdTest;
    }

    /**
     * @return List of all tests
     */
    public static RealmResults<Test> getAllTests() {
        return getDefaultInstance()
                .where(Test.class)
                .findAll();
    }

    /**
     * @return Highest index in the test table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = getDefaultInstance()
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
        getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Test.class);
                    }
                });
    }

    /**
     *Get the last non finished saved test
     *
     * @param type FINAL_TEST_MODE or CUSTOM_TEST_MODE or CHAPTER_TEST_MODE
     * @param testChapterId 0 no specific chapter /n = 1 ...k  for a given chapter
     * @return
     */
    @Nullable
    public static Test getLastSavedTest(int type,int testChapterId) {
        return getDefaultInstance()
                .where(Test.class)
                .equalTo("type", type)
                .equalTo("isTestFinished", false)
                .equalTo("testChapterId",testChapterId)
                .findFirst();

    }


    public static void updateFinishedTest(final Test test) {
        getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        test.setTestFinished(true);
                        test.setFinishTime(System.currentTimeMillis());

                    }
                });
    }

    /**
     * This function clean all unfinished Test with the given Test mode
     *
     * @param finalTestMode
     */
    public static void cleanUnfinishedTest(final int finalTestMode,final int testChapterId) {

        getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Test> result = realm.where(Test.class).equalTo("type", finalTestMode).equalTo("testChapterId", testChapterId).equalTo("isTestFinished", false).findAll();
                        result.deleteAllFromRealm();
                    }
                });
    }
}
