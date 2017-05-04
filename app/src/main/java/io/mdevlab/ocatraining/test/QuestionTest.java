package io.mdevlab.ocatraining.test;

import android.util.Log;

import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.modelManager.QuestionManager;
import io.mdevlab.ocatraining.util.Constants;
import io.realm.RealmList;
import io.realm.RealmResults;

import static io.mdevlab.ocatraining.test.AnswerTest.createRandomAnswers;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class QuestionTest {

    private static final String TAG = QuestionTest.class.getSimpleName();

    public static void deleteQuestions() {
        QuestionManager.deleteQuestions();
    }

    public static void readQuestions() {
        RealmResults<Question> questions = QuestionManager.getAllQuestions();
        for (Question question : questions)
            Log.e(TAG, question.toString());
    }

    public static RealmList<Question> createRandomQuestions(int index) {
        RealmList<Question> questions = new RealmList<>();
        questions.add(createRandomQuestion(index));
        questions.add(createRandomQuestion(index));
        return questions;
    }

    public static Question createRandomQuestion(int index, String question) {
        Question randomQuestion = new Question();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.SINGLE_ANSWER_QUESTION);
        randomQuestion.setStatement(question);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setAnswers(createRandomAnswers(index));
        return randomQuestion;
    }

    public static Question createRandomQuestion(int index) {
        return createRandomQuestion(index, "What is life ?");
    }
}
