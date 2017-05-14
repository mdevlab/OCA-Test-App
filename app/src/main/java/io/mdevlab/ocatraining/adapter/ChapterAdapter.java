package io.mdevlab.ocatraining.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.activity.ActivityChapter;
import io.mdevlab.ocatraining.activity.AllChaptersActivity;
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.util.Constants;

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
                chapterViewHolder.mChapterNumber.setText(String.valueOf(position));
                chapterViewHolder.mChapterName.setText(chapter.getName());
                chapterViewHolder.chapterView.setCardBackgroundColor(ContextCompat.getColor(mContext, colors[position % colors.length]));
                break;

        }


    }


    @Override
    public int getItemCount() {
        return chapterList.size() + 1;
    }


    /**
     * Holder for each chapter
     */
    class ChapterViewHolder extends RecyclerView.ViewHolder {

        int chapterId;

        TextView mChapterNumber;
        TextView mChapterName;
        CardView chapterView;


        ChapterViewHolder(View itemView) {
            super(itemView);

            mChapterNumber = (TextView) itemView.findViewById(R.id.chapter_number);
            mChapterName = (TextView) itemView.findViewById(R.id.chapter_name);
            chapterView = (CardView) itemView.findViewById(R.id.Chapter_card_view);

            chapterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openChapter();
                }
            });
        }

        /**
         * Method that opens the chapter activity while passing on to it
         * the necessary data
         */
        private void openChapter() {
            Intent openChapter = new Intent(mContext, ActivityChapter.class);
            openChapter.putExtra(Constants.CHAPTER_ID, chapterId);
            mContext.startActivity(openChapter);
        }
    }


    /**
     * Header view Holder  this holder is for displaying all chapter header view
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        CardView headerView;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            headerView = (CardView) itemView.findViewById(R.id.all_chapters_container);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AllChaptersActivity.class);
                    mContext.startActivity(intent);

//TODO set Animation
//                            ,
//                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this
//                            ).toBundle());
                }
            });

        }
    }
}
