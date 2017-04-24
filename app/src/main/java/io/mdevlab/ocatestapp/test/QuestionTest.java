package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.modelManager.QuestionManager;
import io.mdevlab.ocatestapp.model.Question;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class QuestionTest {

    private static final String TAG = QuestionTest.class.getSimpleName();

    public static void deleteQuestions() {
        QuestionManager.deleteQuestions();
    }

    public static void readQuestions() {
        RealmResults<Question> questions = QuestionManager.getAllQuestions();
        for (Question question : questions)
            Log.e(TAG, question.toString());
    }
}
