package io.mdevlab.ocatraining.modelManager;

import java.util.Random;

import io.mdevlab.ocatraining.model.Question;
import io.realm.Realm;
import io.realm.RealmResults;

// Todo : Optimize random selection of an element, no need to charge all questions

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class QuestionManager {

    /**
     * @return List of all questions
     */
    public static RealmResults<Question> getAllQuestions() {
        return Realm.getDefaultInstance()
                .where(Question.class)
                .findAll();
    }

    /**
     * @return Question object chosen randomly
     */
    public static Question getRandomQuestion() {
        RealmResults<Question> questions = getAllQuestions();
        return (questions.size() > 0) ? questions.get(getRandomQuestionIndex(questions)) : null;
    }

    /**
     * @param questions: List of all questions
     * @return Random index of an existing question
     */
    private static int getRandomQuestionIndex(RealmResults<Question> questions) {
        Random random = new Random();
        return random.nextInt(questions.size());
    }

    /**
     * @return Highest index in the questions table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Question.class)
                .max(Question.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    /**
     * Delete all questions
     */
    public static void deleteQuestions() {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Question.class);
                    }
                });
    }
}
