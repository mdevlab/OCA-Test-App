package io.mdevlab.ocatraining.test;

import android.util.Log;

import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import io.mdevlab.ocatraining.modelManager.TestManager;
import io.realm.RealmResults;

import static io.mdevlab.ocatraining.model.Test.CUSTOM_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.FINAL_TEST_MODE;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestTest {

    private static final String TAG = TestTest.class.getSimpleName();

    public static Test createTest() {
        io.mdevlab.ocatraining.model.Test test = new io.mdevlab.ocatraining.model.Test();
        test.setId(TestManager.getNextIndex());
        test.setTotalNumberOfQuestions(20);
        test.setNumberOfCompletedQuestions(20);
        test.setType(CUSTOM_TEST_MODE);
        test.setDuration(7320);
        test.setQuestions(ChapterManager.getQuestionsForChapter(1));
        return test;
    }

    private static Test createTest(int index) {
        Test randomTest = new Test();
        randomTest.setId(TestManager.getNextIndex());
        randomTest.setDuration(3600);
        randomTest.setType(FINAL_TEST_MODE);
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
