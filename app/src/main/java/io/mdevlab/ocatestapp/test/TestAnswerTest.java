package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.dao.TestAnswerManager;
import io.mdevlab.ocatestapp.model.TestAnswer;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestAnswerTest {

    private static final String TAG = TestAnswerTest.class.getSimpleName();

    public static void deleteTestAnswers() {
        TestAnswerManager.deleteTestAnswers();
    }

    public static void readTestAnswers() {
        RealmResults<TestAnswer> testAnswers = TestAnswerManager.getAllTestAnswers();

        for (TestAnswer testAnswer : testAnswers)
            Log.e(TAG, testAnswer.toString());
    }
}
