package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.modelManager.AnswerManager;
import io.mdevlab.ocatestapp.model.Answer;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class AnswerTest {

    private static final String TAG = AnswerTest.class.getSimpleName();

    public static void deleteAnswers() {
        AnswerManager.deleteAnswers();
    }

    public static void readAnswers() {
        RealmResults<Answer> answers = AnswerManager.getAllAnswers();
        for (Answer answer : answers)
            Log.e(TAG, answer.toString());
    }
}
