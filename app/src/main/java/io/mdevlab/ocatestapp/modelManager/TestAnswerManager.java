package io.mdevlab.ocatestapp.modelManager;

import io.mdevlab.ocatestapp.model.TestAnswer;
import io.realm.Realm;
import io.realm.RealmResults;

import static io.realm.Realm.getDefaultInstance;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestAnswerManager {

    public static RealmResults<TestAnswer> getAllTestAnswers() {
        return getDefaultInstance()
                .where(TestAnswer.class)
                .findAll();
    }

    /**
     * @return Highest index in the test answer table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(TestAnswer.class)
                .max(TestAnswer.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    /**
     * Delete all test answers
     */
    public static void deleteTestAnswers() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(TestAnswer.class);
            }
        });
    }
}
