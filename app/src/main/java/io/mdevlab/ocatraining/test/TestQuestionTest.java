package io.mdevlab.ocatraining.test;

import android.content.Context;
import android.util.Log;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.modelManager.QuestionManager;
import io.mdevlab.ocatraining.modelManager.TestQuestionManager;
import io.mdevlab.ocatraining.util.Constants;
import io.realm.RealmList;
import io.realm.RealmResults;

import static io.mdevlab.ocatraining.test.TestAnswerTest.createRandomSingleAnswerTestAnswers;
import static io.mdevlab.ocatraining.test.TestAnswerTest.createRandomTestAnswers;

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

    public static TestQuestion createRandomTestQuestion(int index,Context context) {
        TestQuestion randomQuestion = new TestQuestion();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.MULTIPLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setFlagged(false);
        randomQuestion.setStatement(context.getString(R.string.dummy_question));
        randomQuestion.setExplanation(context.getString(R.string.dummy_explanation));
        randomQuestion.setAnswers(createRandomTestAnswers(index));
        return randomQuestion;
    }

    public static TestQuestion createRandomSingleAnswerTestQuestion(int index,Context context) {
        TestQuestion randomQuestion = new TestQuestion();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.SINGLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setFlagged(false);
        randomQuestion.setStatement(context.getString(R.string.dummy_question));
        randomQuestion.setExplanation(context.getString(R.string.dummy_explanation));
        randomQuestion.setAnswers(createRandomSingleAnswerTestAnswers(index));
        return randomQuestion;
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

    public static RealmList<TestQuestion> createRandomTestQuestions(int index) {
        RealmList<TestQuestion> questions = new RealmList<>();
        questions.add(createRandomTestQuestion(index));
        questions.add(createRandomTestQuestion(index));
        return questions;
    }
}
