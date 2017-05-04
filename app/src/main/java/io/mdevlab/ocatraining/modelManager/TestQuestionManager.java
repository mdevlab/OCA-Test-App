package io.mdevlab.ocatraining.modelManager;

import io.mdevlab.ocatraining.model.TestQuestion;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestQuestionManager {

    /**
     * @return List of all test questions
     */
    public static RealmResults<TestQuestion> getAllTestQuestions() {
        return Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .findAll();
    }

    /**
     * @return Highest index in the test question table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .max(TestQuestion.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    /**
     * Delete all test questions
     */
    public static void deleteTestQuestions() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().delete(TestQuestion.class);
            }
        });
    }
}
