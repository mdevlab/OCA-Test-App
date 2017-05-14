package io.mdevlab.ocatraining.test;

import android.util.Log;


import io.mdevlab.ocatraining.model.Answer;
import io.mdevlab.ocatraining.model.TestAnswer;
import io.mdevlab.ocatraining.modelManager.TestAnswerManager;
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
        answers.add(createRandomTestAnswer(index,false));
        answers.add(createRandomTestAnswer(index,true));
        answers.add(createRandomTestAnswer(index,false));
        answers.add(createRandomTestAnswer(index,true));
        answers.add(createRandomTestAnswer(index,false));
        return answers;
    }


    public static RealmList<TestAnswer> createRandomSingleAnswerTestAnswers(int index) {
        RealmList<TestAnswer> answers = new RealmList<>();
        answers.add(createRandomTestAnswer(index,true));
        answers.add(createRandomTestAnswer(index,false));
        answers.add(createRandomTestAnswer(index,false));
        answers.add(createRandomTestAnswer(index,false));
        answers.add(createRandomTestAnswer(index,false));
        return answers;
    }

    private static TestAnswer createRandomTestAnswer(int index,boolean iscorrect) {
        Answer randomAnswer = new Answer();
        randomAnswer.setId(TestAnswerManager.getNextIndex());
        randomAnswer.setAnswer("Answer " + index);
        randomAnswer.setCorrect(iscorrect);
        return new TestAnswer(randomAnswer);
    }
}
