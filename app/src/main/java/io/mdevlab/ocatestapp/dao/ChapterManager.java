package io.mdevlab.ocatestapp.dao;

import io.mdevlab.ocatestapp.model.Chapter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class ChapterManager {

    private static final String TAG = ChapterManager.class.getSimpleName();

    public static void createChapter(final Chapter chapter) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insert(chapter);
                    }
                });
    }

    public static Chapter getChapterById(int chapterId) {
        return Realm.getDefaultInstance()
                .where(Chapter.class)
                .equalTo(Chapter.ID_COLUMN, chapterId)
                .findFirst();
    }

    public static RealmResults<Chapter> getAllChapters() {
        return Realm.getDefaultInstance()
                .where(Chapter.class)
                .findAll();
    }

    public static int getNextIndex() {
        Number currentIdNum = Realm.getDefaultInstance()
                .where(Chapter.class)
                .max(Chapter.ID_COLUMN);
        if (currentIdNum == null)
            return 1;
        else
            return currentIdNum.intValue() + 1;
    }

    public static void deleteChapters() {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Chapter.class);
            }
        });
    }
}
