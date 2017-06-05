package io.mdevlab.ocatraining.modelManager;

import android.content.Context;

import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.util.Helper;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class ChapterManager {


    private static final String CHAPTER_SUMMARY_FILE_PREFIX = "chapter";
    private static final String CHAPTER_SUMMARY_FILE_EXTENSION = ".html";


    /**
     * @param chapter: Chapter object we want to create
     */
    public static void createChapter(final Chapter chapter) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insert(chapter);
                    }
                });
    }

    /**
     * @param chapterId
     * @return Chapter object with the given id
     */
    public static Chapter getChapterById(int chapterId) {
        return Realm.getDefaultInstance()
                .where(Chapter.class)
                .equalTo(Chapter.ID_COLUMN, chapterId)
                .findFirst();
    }

    /**
     * @return List of all the chapters
     */
    public static RealmResults<Chapter> getAllChapters() {
        return Realm.getDefaultInstance()
                .where(Chapter.class)
                .findAll();
    }

    /**
     * @return Highest index in the chapters table + 1
     */
    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Chapter.class)
                .max(Chapter.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    /**
     * Delete all chapters
     */
    public static void deleteChapters() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Chapter.class);
            }
        });
    }

    /**
     * @param chapterId
     * @return List of all questions of a given chapter
     */
    public static RealmList<TestQuestion> getQuestionsForChapter(int chapterId) {
        Chapter chapter = getChapterById(chapterId);

        if (chapter != null) {
            RealmList<TestQuestion> testQuestions = new RealmList<>();
            for (Question question : chapter.getQuestions()) {
                testQuestions.add(new TestQuestion(question));
            }
            return testQuestions;
        }

        return null;
    }


    public static String getSummaryByChapterId(Context context, int chapterId) {
        return Helper.fromAssetsFileToString(context, CHAPTER_SUMMARY_FILE_PREFIX + chapterId + CHAPTER_SUMMARY_FILE_EXTENSION);
    }
}
