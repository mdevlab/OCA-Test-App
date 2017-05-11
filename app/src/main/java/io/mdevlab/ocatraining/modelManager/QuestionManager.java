package io.mdevlab.ocatraining.modelManager;

import java.util.Random;

import io.mdevlab.ocatraining.model.Answer;
import io.mdevlab.ocatraining.model.Question;
import io.realm.Realm;
import io.realm.RealmResults;

// Todo : Optimize random selection of an element, no need to charge all questions

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class QuestionManager {

    private static final String NEW_LINE = System.getProperty("line.separator");


    /**
     * @return List of all questions
     */
    public static RealmResults<Question> getAllQuestions() {
        return Realm.getDefaultInstance()
                .where(Question.class)
                .findAll();
    }


    /**
     * @return Displayable random question
     */
    public static String getRandomQuestionForDisplay() {
        Question question = getRandomQuestion();

        if (question == null)
            return "";

        return buildQuestionForDisplay(question);
    }


    /**
     * @param question Question we want to display (used in notifications)
     * @return The question and it's answers in a displayable format
     */
    private static String buildQuestionForDisplay(Question question) {
        StringBuilder builder = new StringBuilder("");

        builder.append(question.getStatement());
        builder.append(NEW_LINE);
        builder.append(NEW_LINE);

        for (Answer answer : question.getAnswers()) {
            builder.append(answer.getAnswer());
            builder.append(NEW_LINE);
        }

        return builder.toString();
    }


    /**
     * @return Question object chosen randomly
     */
    private static Question getRandomQuestion() {
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
