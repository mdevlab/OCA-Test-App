package io.mdevlab.ocatraining.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.activity.ActivityChapter;
import io.mdevlab.ocatraining.activity.AllChaptersActivity;
import io.mdevlab.ocatraining.activity.TestActivity;
import io.mdevlab.ocatraining.analytics.AnalyticsManager;
import io.mdevlab.ocatraining.model.Chapter;

import static io.mdevlab.ocatraining.model.Chapter.CHAPTER_ID;
import static io.mdevlab.ocatraining.model.Test.CHAPTER_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.TEST_CHAPTER;
import static io.mdevlab.ocatraining.model.Test.TEST_MODE;

/**
 * Created by bachiri on 4/22/17.
 */


public class ChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Chapter> chapterList;

    private final int VIEW_TYPE_HEADER = 1;
    private final int VIEW_TYPE_CHAPTER = 2;

    private final int HEADER_POSITION = 0;


    private int[] colors = {
            R.color.item_color_1,
            R.color.item_color_2,
            R.color.item_color_3,
            R.color.item_color_4,
            R.color.item_color_5,
            R.color.item_color_6
    };


    public ChapterAdapter(Context mContext, List<Chapter> chapterList) {
        this.mContext = mContext;
        this.chapterList = chapterList;
    }


    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        View itemView;

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_all_chapters, parent, false);

                holder = new HeaderViewHolder(itemView);
                break;
            case VIEW_TYPE_CHAPTER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chapter, parent, false);

                holder = new ChapterViewHolder(itemView);
                break;
        }


        return holder;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == HEADER_POSITION)
            return VIEW_TYPE_HEADER;
        else
            return VIEW_TYPE_CHAPTER;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        int position = holder.getAdapterPosition();

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                //Here we can customize our all chapters  header
                break;
            case VIEW_TYPE_CHAPTER:
                Chapter chapter = chapterList.get(position - 1);
                final ChapterViewHolder chapterViewHolder = (ChapterViewHolder) holder;

                chapterViewHolder.chapterId = chapter.getId();
                chapterViewHolder.mChapterName.setText(chapter.getName());
                chapterViewHolder.mChapterView.setCardBackgroundColor(ContextCompat.getColor(mContext, colors[position % colors.length]));
                break;

        }


    }


    @Override
    public int getItemCount() {
        return chapterList.size() + 1;
    }


    private class ChapterViewHolder extends RecyclerView.ViewHolder {

        int chapterId;

        TextView mChapterName;
        CardView mChapterView;
        ImageView mStartTest;


        ChapterViewHolder(View itemView) {
            super(itemView);

            mChapterName = (TextView) itemView.findViewById(R.id.chapter_name);

            mStartTest = (ImageView) itemView.findViewById(R.id.chapter_start_test);
            mStartTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent test = new Intent(mContext, TestActivity.class);
                    test.putExtra(TEST_CHAPTER, chapterId);
                    test.putExtra(TEST_MODE, CHAPTER_TEST_MODE);
                    mContext.startActivity(test);
                    trackTakeChapterTest();

                }


            });

            mChapterView = (CardView) itemView.findViewById(R.id.Chapter_card_view);
            mChapterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openChapter();
                    trackOpenChapterEvent();
                }
            });
        }

        /**
         * Firebase Analytics tracking
         *
         * Track take the chapter test
         */
        private void trackTakeChapterTest() {
            Bundle bundle = new Bundle();
            bundle.putString(mContext.getString(R.string.property_name_source), mContext.getString(R.string.attribute_value_home));
            bundle.putString(mContext.getString(R.string.property_name_chapter_id), String.valueOf(chapterId));
            AnalyticsManager.getInstance().logEvent(mContext.getString(R.string.event_click_take_chapter_test), bundle);

        }

        /**
         * Firebase Analytics tracking
         *
         * Track open the chapter view
         */
        private void trackOpenChapterEvent() {
            Bundle bundle = new Bundle();
            bundle.putString(mContext.getResources().getString(R.string.property_name_chapter_id), String.valueOf(chapterId));
            AnalyticsManager.getInstance().logEvent(mContext.getResources().getString(R.string.event_view_all_chapters), null);
        }

        /**
         * Method that opens the chapter activity while passing on to it
         * the necessary data
         */
        private void openChapter() {
            Intent openChapter = new Intent(mContext, ActivityChapter.class);
            openChapter.putExtra(CHAPTER_ID, chapterId);
            mContext.startActivity(openChapter);
        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        CardView mHeaderView;

        HeaderViewHolder(View itemView) {
            super(itemView);

            mHeaderView = (CardView) itemView.findViewById(R.id.all_chapters_container);
            mHeaderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AllChaptersActivity.class);
                    mContext.startActivity(intent);
                    //Firebase Analytics tracking
                    AnalyticsManager.getInstance().logEvent(mContext.getResources().getString(R.string.event_view_all_chapters),null);
                }
            });
        }
    }
}
