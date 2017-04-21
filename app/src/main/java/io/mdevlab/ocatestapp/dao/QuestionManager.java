package io.mdevlab.ocatestapp.dao;

import android.util.Log;

import java.util.Random;

import io.mdevlab.ocatestapp.model.Question;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class QuestionManager {

    private static final String TAG = QuestionManager.class.getSimpleName();

    public static void createQuestion(final Question question) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(question);
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                Log.e(TAG, "question created!");
            }
        }, new Realm.Transaction.OnError() {

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "question not created!");
            }
        });
    }

    public static Question getQuestionById(int questionId) {
        return Realm.getDefaultInstance()
                .where(Question.class)
                .equalTo(Question.ID_COLUMN, questionId)
                .findFirst();
    }

    public static RealmResults<Question> getAllQuestions() {
        return Realm.getDefaultInstance()
                .where(Question.class)
                .findAll();
    }

    // Todo : Optimize random selection of an element, no need to charge all questions
    public static Question getRandomQuestion() {
        RealmResults<Question> questions = getAllQuestions();
        return (questions.size() > 0) ? questions.get(getRandomQuestionIndex(questions)) : null;
    }

    private static int getRandomQuestionIndex(RealmResults<Question> questions) {
        Random random = new Random();
        return random.nextInt(questions.size());
    }

    public static void setQuestionAsFavorite(int questionId, boolean isFavorite) {
        Question questionToUpdate = getQuestionById(questionId);

        if (questionToUpdate != null)
            questionToUpdate.setFavorite(isFavorite);
    }

    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Question.class)
                .max(Question.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

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
