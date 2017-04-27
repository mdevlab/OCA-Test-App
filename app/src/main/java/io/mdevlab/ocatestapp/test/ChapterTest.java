package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.modelManager.ChapterManager;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class ChapterTest {

    private static final String TAG = ChapterTest.class.getSimpleName();

    public static void deleteChapters() {
        ChapterManager.deleteChapters();
    }

    public static void readChapters() {
        RealmResults<Chapter> chapters = ChapterManager.getAllChapters();
        for (Chapter chapter : chapters)
            Log.e(TAG, chapter.toString());
    }

    public static Chapter createChapter(int index) {
        Chapter randomChapter = new Chapter();
        randomChapter.setId(ChapterManager.getNextIndex());
        randomChapter.setName("Chapter " + index);
        randomChapter.setSummary("Summary " + index);
        randomChapter.setQuestions(QuestionTest.createRandomQuestions(index));
        return randomChapter;
    }

    private static void generateChapter() {
        ChapterManager.createChapter(createChapter(200));
    }

    public static void runAllTest() {
        deleteChapters();
        generateChapter();
        readChapters();
    }
}
