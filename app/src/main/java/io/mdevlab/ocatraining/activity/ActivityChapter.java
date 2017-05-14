package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import io.mdevlab.ocatraining.util.Constants;


public class ActivityChapter extends AppCompatActivity {

    final String TAG = ActivityChapter.class.getSimpleName();

    Chapter currentChapter;
    Toolbar toolbar;
    HtmlTextView chapterSummary;
    FloatingActionButton chapterStartTest;

    final String dummy_html_page = "<HTML>  <BODY BGCOLOR=\"FFFFFF\"> <CENTER><IMG ALIGN=\"BOTTOM\" SRC=\"clouds.jpg\" /> </CENTER> <HR> <a href=\"http://somegreatsite.com\">Link Name</a> is a link to another nifty site <H1>This is a Header</H1> <H2>This is a Medium Header</H2> Send me mail at <a href=\"mailto:support@yourcompany.com\"> support@yourcompany.com</a>. <P> This is a new paragraph! </P> <P> <B>This is a new paragraph!</B> </P> <BR /> <B><I>This is a new sentence without a paragraph break, in bold italics.</I></B> </HR> </BODY> </HTML>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        currentChapter = currentChapter();

        setUpToolbar();
        setUpChapterSummary();
        setUpStartTestFab();
    }


    private Chapter currentChapter() {
        int chapterId = getIntent().getIntExtra(Constants.CHAPTER_ID, -1);
        return ChapterManager.getChapterById(chapterId);
    }


    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(currentChapter != null ? currentChapter.getName() : getString(R.string.title_activity_chapter));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setUpChapterSummary() {
        chapterSummary = (HtmlTextView) findViewById(R.id.chapter_summary);
        chapterSummary.setHtml(dummy_html_page);
    }


    private void setUpStartTestFab() {
        chapterStartTest = (FloatingActionButton) findViewById(R.id.chapter_start_test);
        chapterStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityChapter.this, "Start test", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
