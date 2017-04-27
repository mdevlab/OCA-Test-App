package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.model.TestAnswer;
import io.mdevlab.ocatestapp.modelManager.TestAnswerManager;
import io.realm.RealmList;
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

    public static RealmList<TestAnswer> createRandomTestAnswers(int index) {
        RealmList<TestAnswer> answers = new RealmList<>();
        answers.add(createRandomTestAnswer(index));
        answers.add(createRandomTestAnswer(index));
        return answers;
    }

    private static TestAnswer createRandomTestAnswer(int index) {
        TestAnswer randomAnswer = new TestAnswer();
        randomAnswer.setId(TestAnswerManager.getNextIndex());
        randomAnswer.setAnswer("Answer " + index);
        randomAnswer.setSelected(false);
        return randomAnswer;
    }
}
