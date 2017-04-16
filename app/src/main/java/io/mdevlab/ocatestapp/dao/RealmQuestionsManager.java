package io.mdevlab.ocatestapp.dao;

import android.content.Context;
import android.widget.Toast;

import java.util.Random;

import io.mdevlab.ocatestapp.model.question.Question;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class RealmQuestionsManager {

    private static RealmQuestionsManager instance;
    private static Context context;

    public static RealmQuestionsManager with(Context context) {
        if (instance == null)
            instance = new RealmQuestionsManager(context);
        return instance;
    }

    private RealmQuestionsManager(Context context) {
        RealmQuestionsManager.context = context;
    }

    public static void createQuestion(final Question question) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(question);
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                Toast.makeText(context, "question created !", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {

            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, "Error while creating question !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Question getQuestionById(int questionId) {
        return Realm.getDefaultInstance()
                .where(Question.class)
                .equalTo("id", questionId)
                .findFirst();
    }

    public static RealmResults<Question> getAllQuestions() {
        return Realm.getDefaultInstance()
                .where(Question.class)
                .findAll();
    }

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
}
