package io.mdevlab.ocatestapp.dao;

import android.content.Context;
import android.widget.Toast;

import java.util.Random;

import io.mdevlab.ocatestapp.model.question.TestQuestion;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class RealmTestQuestionsManager {

    private static RealmTestQuestionsManager instance;
    private static Context context;

    public static RealmTestQuestionsManager with(Context context) {
        if (instance == null)
            instance = new RealmTestQuestionsManager(context);
        return instance;
    }

    private RealmTestQuestionsManager(Context context) {
        RealmTestQuestionsManager.context = context;
    }

    public static void createTestQuestion(final TestQuestion question) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(question);
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                Toast.makeText(context, "test question created !", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {

            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, "Error while creating test question !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static TestQuestion getTestQuestionById(int questionId) {
        return Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .equalTo("id", questionId)
                .findFirst();
    }

    public static RealmResults<TestQuestion> getAllTestQuestions() {
        return Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .findAll();
    }

    public static TestQuestion getRandomTestQuestion() {
        RealmResults<TestQuestion> questions = getAllTestQuestions();
        return (questions.size() > 0) ? questions.get(getRandomQuestionIndex(questions)) : null;
    }

    private static int getRandomQuestionIndex(RealmResults<TestQuestion> questions) {
        Random random = new Random();
        return random.nextInt(questions.size());
    }

    public static void setTestQuestionAsFavorite(int questionId, boolean isFavorite) {
        TestQuestion questionToUpdate = getTestQuestionById(questionId);

        if (questionToUpdate != null)
            questionToUpdate.setFavorite(isFavorite);
    }

    public static void setTestQuestionAsFlagged(int questionId, boolean isFlagged) {
        TestQuestion questionToUpdate = getTestQuestionById(questionId);

        if (questionToUpdate != null)
            questionToUpdate.setFlagged(isFlagged);
    }
}
