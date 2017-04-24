package io.mdevlab.ocatestapp.modelManager;

import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.model.Question;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class ChapterManager {

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
}
