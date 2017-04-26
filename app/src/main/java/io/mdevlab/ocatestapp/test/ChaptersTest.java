package io.mdevlab.ocatestapp.test;


import java.util.ArrayList;
import java.util.List;

import io.mdevlab.ocatestapp.model.Chapter;

/**
 * Created by bachiri on 4/25/17.
 */

public class ChaptersTest {


    /**
     * Dummy Chapters 1,2,3,4,5,6
     *
     * @return
     */
    public static List<Chapter> prepareChapters() {
        List<Chapter> chapterList = new ArrayList<>();
        chapterList.add(ChapterTest.createChapter(0));
        chapterList.add(ChapterTest.createChapter(1));
        chapterList.add(ChapterTest.createChapter(2));
        chapterList.add(ChapterTest.createChapter(3));
        chapterList.add(ChapterTest.createChapter(4));
        chapterList.add(ChapterTest.createChapter(5));
        return chapterList;
    }

}
