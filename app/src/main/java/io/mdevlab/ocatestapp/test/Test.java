package io.mdevlab.ocatestapp.test;

import android.util.Log;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class Test {

    private static final String TAG = Test.class.getSimpleName();

    public static void runAllTests() {
        TestAnswerTest.deleteTestAnswers();
        TestQuestionTest.deleteTestQuestions();
        TestTest.runAllTest();
        TestQuestionTest.readTestQuestions();
        TestAnswerTest.readTestAnswers();

        Log.e(TAG, "-----------------");
        Log.e(TAG, " ");
        Log.e(TAG, "-----------------");

        AnswerTest.deleteAnswers();
        QuestionTest.deleteQuestions();
        ChapterTest.runAllTest();
        QuestionTest.readQuestions();
        AnswerTest.readAnswers();
    }
}
