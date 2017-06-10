package io.mdevlab.ocatraining.util;

import android.content.Context;

import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.modelManager.AnswerManager;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import io.mdevlab.ocatraining.modelManager.QuestionManager;
import io.mdevlab.ocatraining.modelManager.TestAnswerManager;
import io.mdevlab.ocatraining.modelManager.TestManager;
import io.mdevlab.ocatraining.modelManager.TestQuestionManager;
import io.realm.Realm;

/**
 * Created by husaynhakeem on 6/10/17.
 */

public class UtilData {


    private static final String DATABASE_NAME = "chapters.json";


    public static boolean isDatabaseEmpty() {
        return Realm.getDefaultInstance().isEmpty();
    }


    public static void emptyDatabase() {
        TestQuestionManager.deleteTestQuestions();
        TestAnswerManager.deleteTestAnswers();
        QuestionManager.deleteQuestions();
        AnswerManager.deleteAnswers();
        ChapterManager.deleteChapters();
        TestManager.deleteAllTests();
    }


    public static void populateDatabase(Context context) {
        Mapper.instance().fromJsonToRealm(context, DATABASE_NAME, Chapter.class);
    }
}
