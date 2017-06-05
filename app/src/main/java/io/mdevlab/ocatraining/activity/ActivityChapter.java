package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.analytics.AnalyticsManager;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.modelManager.ChapterManager;

import static io.mdevlab.ocatraining.model.Chapter.CHAPTER_ID;
import static io.mdevlab.ocatraining.model.Test.CHAPTER_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.TEST_CHAPTER;
import static io.mdevlab.ocatraining.model.Test.TEST_MODE;


public class ActivityChapter extends ActivityBase {

    final String TAG = ActivityChapter.class.getSimpleName();

    Chapter currentChapter;
    HtmlTextView chapterSummary;
    FloatingActionButton chapterStartTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        currentChapter = currentChapter();

        setUpToolbar(currentChapter != null ? currentChapter.getName() : getString(R.string.title_activity_chapter));
        setUpDFP(null);
        setUpChapterSummary();
        setUpStartTestFab();
    }


    private Chapter currentChapter() {
        int chapterId = getIntent().getIntExtra(CHAPTER_ID, -1);
        return ChapterManager.getChapterById(chapterId);
    }


    private void setUpChapterSummary() {
        chapterSummary = (HtmlTextView) findViewById(R.id.chapter_summary);

        String summary = ChapterManager.getSummaryByChapterId(ActivityChapter.this, currentChapter.getId());
        chapterSummary.setHtml(summary == null ? "" : summary);
    }


    private void setUpStartTestFab() {
        chapterStartTest = (FloatingActionButton) findViewById(R.id.chapter_start_test);
        chapterStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent test = new Intent(ActivityChapter.this, TestActivity.class);
                test.putExtra(TEST_CHAPTER, currentChapter.getId());
                test.putExtra(TEST_MODE, CHAPTER_TEST_MODE);
                startActivity(test);
                trackTakeChapterTest();
            }
        });
    }

    /**
     * Firebase Analytics tracking
     * <p>
     * Track take the chapter test
     */
    private void trackTakeChapterTest() {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.property_name_source), getString(R.string.attribute_value_chapter));
        bundle.putString(getString(R.string.property_name_chapter_id), String.valueOf(currentChapter.getId()));
        AnalyticsManager.getInstance().logEvent(getString(R.string.event_click_take_chapter_test), bundle);

    }
}
