package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.dao.TestQuestionManager;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestQuestionTest {

    private static final String TAG = TestQuestionTest.class.getSimpleName();

    public static void deleteTestQuestions() {
        TestQuestionManager.deleteTestQuestions();
    }

    public static void readTestQuestions() {
        RealmResults<TestQuestion> testQuestions = TestQuestionManager.getAllTestQuestions();

        for (TestQuestion testQuestion : testQuestions)
            Log.e(TAG, testQuestion.toString());
    }
}
