package io.mdevlab.ocatestapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.model.Chapter;

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
      
        holder.mchapternumber.setText(String.valueOf(position));
        holder.mchaptername.setText(chapter.getName());
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(mContext, colors[position % colors.length]));
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mchapternumber;
        public TextView mchaptername;
        public CardView cardView;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            mchapternumber = (TextView) itemView.findViewById(R.id.chapter_number);
            mchaptername = (TextView) itemView.findViewById(R.id.chapter_name);
            cardView =  (CardView) itemView.findViewById(R.id.Chapter_card_view);
        }
    }
}
