package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.model.Test;
import io.mdevlab.ocatestapp.modelManager.ChapterManager;
import io.mdevlab.ocatestapp.modelManager.TestManager;
import io.mdevlab.ocatestapp.util.Constants;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestTest {

    private static final String TAG = TestTest.class.getSimpleName();

    public static Test createTest() {
        io.mdevlab.ocatestapp.model.Test test = new io.mdevlab.ocatestapp.model.Test();
        test.setId(TestManager.getNextIndex());
        test.setTotalNumberOfQuestions(20);
        test.setNumberOfCompletedQuestions(20);
        test.setType(Constants.CUSTOMIZED_TEST);
        test.setDuration(7320);
        test.setQuestions(ChapterManager.getQuestionsForChapter(1));
        return test;
    }

    private static Test createTest(int index) {
        Test randomTest = new Test();
        randomTest.setId(TestManager.getNextIndex());
        randomTest.setDuration(3600);
        randomTest.setType(Constants.FINAL_TEST);
        randomTest.setNumberOfCompletedQuestions(70);
        randomTest.setTotalNumberOfQuestions(77);
        randomTest.setQuestions(TestQuestionTest.createRandomTestQuestions(index));
        return randomTest;
    }

    private static void generateTest() {
        TestManager.createTest(createTest(100));
    }

    private static void readTests() {
        Log.e(TAG, "Reading tests..");

        RealmResults<Test> tests = TestManager.getAllTests();
        for (Test test : tests)
            Log.e(TAG, test.toString());
    }

    private static void deleteTests() {
        TestManager.deleteAllTests();
    }

    public static void runAllTest() {
        deleteTests();
        generateTest();
        readTests();
    }
}
