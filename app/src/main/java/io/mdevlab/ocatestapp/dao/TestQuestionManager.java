package io.mdevlab.ocatestapp.dao;

import android.content.Context;

import java.util.Random;

import io.mdevlab.ocatestapp.model.TestQuestion;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestQuestionManager {

    private static TestQuestionManager instance;
    private static Context context;

    public static TestQuestionManager with(Context context) {
        if (instance == null)
            instance = new TestQuestionManager(context);
        return instance;
    }

    private TestQuestionManager(Context context) {
        TestQuestionManager.context = context;
    }

    public static void createTestQuestion(final TestQuestion question) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(question);
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

    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(TestQuestion.class)
                .max(TestQuestion.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    public static void deleteTestQuestions() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().delete(TestQuestion.class);
            }
        });
    }
}
