package io.mdevlab.ocatraining.modelManager;

import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestQuestionManager {

    /**
     * @return List of all test questions
     */
    public static RealmList<TestQuestion> getTestQuestions(int limit) {

        //TODO Implement intelligent getter for retrieving unique bunch of questions
        RealmResults<Question> questionRealmResults = QuestionManager.getAllQuestions();

        int currentCounter = 0;
        RealmList<TestQuestion> realmList = new RealmList<>();

        for (Question question : questionRealmResults) {
            if (currentCounter < limit) {
                realmList.add(new TestQuestion(question));
                currentCounter++;
            }
        }

        return realmList;
    }


    /**
     * @return List of all test questions
     */
    public static RealmList<TestQuestion> getChapterTestQuestions(int limit, int chapter) {

        //TODO Implement intelligent getter for retrieving unique bunch of questions
        RealmResults<Question> questionRealmResults = QuestionManager.getAllChpaterQuestions(chapter);

        int currentCounter = 0;
        RealmList<TestQuestion> realmList = new RealmList<>();
        for (Question question : questionRealmResults) {
            if (currentCounter < limit) {
                realmList.add(new TestQuestion(question));
                currentCounter++;
            }

        }

        return realmList;


    }


    /**
     * Get a random Question
     *
     * @return
     */
    public static TestQuestion getRandomQuestion() {

        return new TestQuestion(QuestionManager.getRandomQuestion());
    }


    /**
     * @return Highest index in the test question table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .max(TestQuestion.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }


    /**
     * Delete all test questions
     */
    public static void deleteTestQuestions() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().delete(TestQuestion.class);
            }
        });
    }


    /**
     * When a question favorite attribute is updated, all the TestQuestion objects
     * that reference it must have their isFavorite attribute also updated.
     * This method does exactly that.
     *
     * @param questionId Id of question whose isFavorite attribute is being modified
     * @param isFavorite Whether the question is being saved or unsaved
     */
    public static void updateAllTestQuestionsFavorite(int questionId, boolean isFavorite) {
        RealmResults<TestQuestion> questions = getTestQuestionsByChapterId(questionId);
        if (questions != null) {
            for (TestQuestion question : questions) {
                updateTestQuestionFavorite(question, isFavorite);
            }
        }
    }


    private static RealmResults<TestQuestion> getTestQuestionsByChapterId(int questionId) {
        return Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .equalTo(TestQuestion.QUESTION_ID_COLUMN, questionId)
                .findAll();
    }


    private static void updateTestQuestionFavorite(TestQuestion question, Boolean isFavorite) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        question.setFavorite(isFavorite);
        realm.commitTransaction();
        realm.close();
    }
}
