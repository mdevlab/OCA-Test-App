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
import io.mdevlab.ocatraining.model.Chapter;
import io.mdevlab.ocatraining.util.Constants;

/**
 * Created by bachiri on 4/22/17.
 */


public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private Context mContext;
    private List<Chapter> chapterList;


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


    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);

        holder.chapterId = chapter.getId();
        holder.mChapterNumber.setText(String.valueOf(position));
        holder.mChapterName.setText(chapter.getName());
        holder.chapterView.setCardBackgroundColor(ContextCompat.getColor(mContext, colors[position % colors.length]));
    }


    @Override
    public int getItemCount() {
        return chapterList.size();
    }


    class ChapterViewHolder extends RecyclerView.ViewHolder {

        int chapterId;

        TextView mChapterNumber;
        TextView mChapterName;
        CardView chapterView;


        ChapterViewHolder(View itemView) {
            super(itemView);

            mChapterNumber = (TextView) itemView.findViewById(R.id.chapter_number);
            mChapterName = (TextView) itemView.findViewById(R.id.chapter_name);
            chapterView =  (CardView) itemView.findViewById(R.id.Chapter_card_view);

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
}
