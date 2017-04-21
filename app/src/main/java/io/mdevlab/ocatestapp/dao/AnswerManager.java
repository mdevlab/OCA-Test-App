package io.mdevlab.ocatestapp.dao;

import android.util.Log;

import io.mdevlab.ocatestapp.model.Answer;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class AnswerManager {

    private static final String TAG = AnswerManager.class.getSimpleName();

    public static void createAnswer(final Answer answer) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(answer);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Answer created !");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Answer not created !");
            }
        });
    }

    public static RealmResults<Answer> getAllAnswers() {
        return Realm.getDefaultInstance()
                .where(Answer.class)
                .findAll();
    }

    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Answer.class)
                .max(Answer.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    public static void deleteAnswers() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Answer.class);
            }
        });
    }
}
