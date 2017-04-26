package io.mdevlab.ocatestapp.adapter;

import android.content.Context;
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

    public ChapterAdapter(Context mContext, List<Chapter> chapterList) {
        this.mContext = mContext;
        this.chapterList = chapterList;
    }

    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chapter_item_layout, parent, false);
        return new ChapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.mChapterNumber.setText("Chapter " + position);
        holder.mChapterName.setText(chapter.getName());

    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {

        TextView mChapterNumber;
        TextView mChapterName;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            mChapterNumber = (TextView) itemView.findViewById(R.id.chapter_number);
            mChapterName = (TextView) itemView.findViewById(R.id.chapter_name);
        }
    }
}
