package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.model.answer.Answer;
import io.mdevlab.ocatestapp.model.question.Question;
import io.mdevlab.ocatestapp.util.Constants;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class RealmQuestionTest {

    private static final String TAG = RealmQuestionTest.class.getSimpleName();

    static void runAllTest() {
        Log.e(TAG, "Running questions tests starting...");
        deleteQuestions();
        createQuestions();
    }

    private static void deleteQuestions() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().delete(Question.class);
            }
        });
    }

    private static void createQuestions() {
        Log.e(TAG, "Creating questions starting...");

        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 1; i <= 20; i++) {
                    Realm.getDefaultInstance().copyToRealm(generateQuestion(i));
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Questions creation completed!");
                readQuestions();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Questions creation error!");
            }
        });
    }

    private static Question generateQuestion(int index) {
        Question question = new Question();
        question.setId(index);
        question.setType(Constants.MULTIPLE_ANSWER_QUESTION);
        question.setExplanation("Explanation " + index);
        question.setFavorite(true);
        question.setAnswers(new RealmList<Answer>());
        return question;
    }

    private static void readQuestions() {
        Log.e(TAG, "Reading questions starting...");

        RealmResults<Question> questions = Realm.getDefaultInstance().where(Question.class).findAll();
        for (Question question : questions)
            Log.e(TAG, question.toString());
    }
}
