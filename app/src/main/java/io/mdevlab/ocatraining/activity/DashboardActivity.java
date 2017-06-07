package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.List;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.modelManager.ChapterManager;
import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;

import static io.mdevlab.ocatraining.activity.TestChartActivity.CHAPTER_DASHBOARD;
import static io.mdevlab.ocatraining.model.Test.TEST_CHAPTER;
import static io.mdevlab.ocatraining.model.Test.TEST_MODE;


public class DashboardActivity extends ActivityBase {

    private CardView finalTestCardView;
    private TagView chapterTagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpToolbar(getString(R.string.title_activity_dashboard));
        finalTestCardView = (CardView) findViewById(R.id.final_test_dashboard);
        finalTestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, TestChartActivity.class);

                intent.putExtra(TEST_MODE, Test.FINAL_TEST_MODE);
                startActivity(intent);
            }
        });

        chapterTagView = (TagView) findViewById(R.id.tag_view);
        addChaptersTags();

        chapterTagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(int position, Tag tag) {
                Intent intent = new Intent(DashboardActivity.this, TestChartActivity.class);
                if (position == 0) {
                    intent.putExtra(TEST_MODE, Test.CUSTOM_TEST_MODE);
                } else {
                    intent.putExtra(TEST_MODE, Test.CHAPTER_TEST_MODE);
                    intent.putExtra(CHAPTER_DASHBOARD, tag.text);
                    intent.putExtra(TEST_CHAPTER, tag.id);

                }
                startActivity(intent);

            }
        });


    }

    private void addChaptersTags() {
        addCoreChapterTag();
        List<Chapter> chapters = ChapterManager.getAllChapters();
        for (Chapter chapter : chapters) {
            addChapterTag(chapter);
        }

    }

    private void addChapterTag(Chapter chapter) {
        Tag tag = new Tag(chapter.getName());
        tag.tagTextColor = Color.WHITE;
        tag.layoutColor = Color.parseColor(chapter.getColor());
        tag.tagTextSize = 17f;
        tag.id = chapter.getId();
        chapterTagView.addTag(tag);
    }

    private void addCoreChapterTag() {
        Tag tag = new Tag(getString(R.string.core_java));
        tag.tagTextColor = Color.WHITE;
        tag.layoutColor = getColor(R.color.item_color_all_chapter);
        tag.tagTextSize = 17f;
        chapterTagView.addTag(tag);
    }
}
