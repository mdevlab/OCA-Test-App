package io.mdevlab.ocatestapp.modelManager;

import io.mdevlab.ocatestapp.model.Answer;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class AnswerManager {

    /**
     * @param id
     * @return Answer object with the given id
     */
    public static Answer getAnswerById(int id) {
        return Realm.getDefaultInstance()
                .where(Answer.class)
                .equalTo(Answer.ID_COLUMN, id)
                .findFirst();
    }

    /**
     * @return List of all answers
     */
    public static RealmResults<Answer> getAllAnswers() {
        return Realm.getDefaultInstance()
                .where(Answer.class)
                .findAll();
    }

    /**
     * @return Highest index in the answers table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Answer.class)
                .max(Answer.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    /**
     * Delete all the answers
     */
    public static void deleteAnswers() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Answer.class);
            }
        });
    }
}
