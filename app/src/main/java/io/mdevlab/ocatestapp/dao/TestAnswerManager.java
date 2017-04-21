package io.mdevlab.ocatestapp.dao;

import io.mdevlab.ocatestapp.model.TestAnswer;
import io.realm.Realm;
import io.realm.RealmResults;

import static io.realm.Realm.getDefaultInstance;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestAnswerManager {

    private static final String TAG = TestAnswerManager.class.getSimpleName();

    public static void createTestAnswer(final TestAnswer testAnswer) {
        getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(testAnswer);
            }
        });
    }

    private static TestAnswer getTestAnswerById(int answerId) {
        return Realm.getDefaultInstance()
                .where(TestAnswer.class)
                .equalTo(TestAnswer.ID_COLUMN, answerId)
                .findFirst();
    }

    public static RealmResults<TestAnswer> getAllTestAnswers() {
        return getDefaultInstance()
                .where(TestAnswer.class)
                .findAll();
    }

    public static void setTestAnswerAsSelected(int answerId, final boolean isSelected) {
        final TestAnswer answerToUpdate = getTestAnswerById(answerId);
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (answerToUpdate != null)
                    answerToUpdate.setSelected(isSelected);
            }
        });
    }

    public static void deleteTestAnswer(int answerId) {
        final TestAnswer answerToDelete = getTestAnswerById(answerId);
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (answerToDelete != null)
                    answerToDelete.deleteFromRealm();
            }
        });
    }

    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(TestAnswer.class)
                .max(TestAnswer.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    public static void deleteTestAnswers() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(TestAnswer.class);
            }
        });
    }
}
