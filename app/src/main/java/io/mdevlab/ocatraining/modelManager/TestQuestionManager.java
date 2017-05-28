package io.mdevlab.ocatraining.modelManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        //Realm result
        RealmResults<Question> questionRealmResults = QuestionManager.getAllQuestions();
        RealmList<TestQuestion> realmList = getRandomLimitRealmListFromRealmResult(limit, questionRealmResults);

        return realmList;
    }


    /**
     * Get n=limit number of Questions from the RealmResults
     * and return a RealmList
     * @param limit
     * @param questionRealmResults
     * @return
     */
    private static RealmList<TestQuestion> getRandomLimitRealmListFromRealmResult(int limit, RealmResults<Question> questionRealmResults) {
        //Realm List to be returned
        RealmList<TestQuestion> realmList = new RealmList<>();
        //List of unique random questions ids
        List<Integer> questionIds = new ArrayList<>();
        //Random Instance to generate Questions Ids
        Random random = new Random();

        int randomNumber;

        while (questionIds.size() < limit) {
            randomNumber = random.nextInt(questionRealmResults.size());
            if (!questionIds.contains(randomNumber)) {
                questionIds.add(randomNumber);
                realmList.add(new TestQuestion(questionRealmResults.get(randomNumber)));
            }


        }
        return realmList;
    }


    /**
     * @return List of all test questions
     */
    public static RealmList<TestQuestion> getChapterTestQuestions(int limit, int chapter) {

        RealmResults<Question> questionRealmResults = QuestionManager.getAllChpaterQuestions(chapter);
        RealmList<TestQuestion> realmList = getRandomLimitRealmListFromRealmResult(limit, questionRealmResults);

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
