package io.mdevlab.ocatestapp.test;

import android.content.Context;
import android.util.Log;

import io.mdevlab.ocatestapp.modelManager.ChapterManager;
import io.mdevlab.ocatestapp.modelManager.TestManager;
import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.util.Constants;
import io.mdevlab.ocatestapp.util.Mapper;

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

    public static void runChapterJsonToRealmTest(Context context) {
        Log.e(TAG, "Started deleting..");
        AnswerTest.deleteAnswers();
        QuestionTest.deleteQuestions();
        ChapterTest.deleteChapters();

        Mapper.instance().fromJsonToRealm(context, "chapters.json", Chapter.class);

        Log.e(TAG, "Started reading..");
        ChapterTest.readChapters();
        QuestionTest.readQuestions();
        AnswerTest.readAnswers();
    }

    public static io.mdevlab.ocatestapp.model.Test createTest() {
        io.mdevlab.ocatestapp.model.Test test = new io.mdevlab.ocatestapp.model.Test();
        test.setId(TestManager.getNextIndex());
        test.setTotalNumberOfQuestions(20);
        test.setNumberOfCompletedQuestions(20);
        test.setType(Constants.CUSTOMIZED_TEST);
        test.setDuration(7320);
        test.setQuestions(ChapterManager.getQuestionsForChapter(1));
        return test;
    }
}
