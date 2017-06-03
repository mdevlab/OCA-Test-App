package io.mdevlab.ocatraining.test;

import io.mdevlab.ocatraining.model.Answer;
import io.mdevlab.ocatraining.modelManager.AnswerManager;
import io.realm.RealmList;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class AnswerTest {

    private static final String TAG = AnswerTest.class.getSimpleName();

    public static void deleteAnswers() {
        AnswerManager.deleteAnswers();
    }

    public static void readAnswers() {
//        RealmResults<Answer> answers = AnswerManager.getAllAnswers();
//        for (Answer answer : answers)
//            Log.e(TAG, answer.toString());
    }

    private static Answer createRandomAnswer(int index) {
        Answer randomAnswer = new Answer();
        randomAnswer.setId(AnswerManager.getNextIndex());
        randomAnswer.setAnswer("Answer " + index);
        randomAnswer.setCorrect(true);
        return randomAnswer;
    }

    public static RealmList<Answer> createRandomAnswers(int index) {
        RealmList<Answer> answers = new RealmList<>();
        answers.add(createRandomAnswer(index));
        answers.add(createRandomAnswer(index));
        return answers;
    }
}
