package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.modelManager.TestAnswerManager;
import io.mdevlab.ocatestapp.modelManager.TestManager;
import io.mdevlab.ocatestapp.modelManager.TestQuestionManager;
import io.mdevlab.ocatestapp.model.Test;
import io.mdevlab.ocatestapp.model.TestAnswer;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.util.Constants;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class TestTest {

    private static final String TAG = TestTest.class.getSimpleName();

    private static Test createTest(int index) {
        Test randomTest = new Test();
        randomTest.setId(TestManager.getNextIndex());
        randomTest.setDuration(3600);
        randomTest.setType(Constants.FINAL_TEST);
        randomTest.setNumberOfCompletedQuestions(70);
        randomTest.setTotalNumberOfQuestions(77);
        randomTest.setQuestions(createRandomTestQuestions(index));
        return randomTest;
    }

    private static RealmList<TestQuestion> createRandomTestQuestions(int index) {
        RealmList<TestQuestion> questions = new RealmList<>();
        questions.add(createRandomTestQuestion(index));
        questions.add(createRandomTestQuestion(index));
        return questions;
    }

    private static TestQuestion createRandomTestQuestion(int index) {
        TestQuestion randomQuestion = new TestQuestion();
        randomQuestion.setId(TestQuestionManager.getNextIndex());
        randomQuestion.setType(Constants.SINGLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setFlagged(false);
        randomQuestion.setAnswers(createRandomTestAnswers(index));
        return randomQuestion;
    }

    private static RealmList<TestAnswer> createRandomTestAnswers(int index) {
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
