package io.mdevlab.ocatraining.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.TestQuestion;

/**
 * Created by husaynhakeem on 4/23/17.
 */

public class ResultsAdapter extends ArrayAdapter<TestQuestion> {

    private Context context;
    private List<TestQuestion> questions;


    public ResultsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TestQuestion> questions) {
        super(context, resource, questions);
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false);

        TestQuestion question = questions.get(position);

        TextView questionNumber = (TextView) convertView.findViewById(R.id.question_number);
        questionNumber.setTextColor(question.hasBeenAnsweredCorrectly() ? ContextCompat.getColor(context, R.color.correct_color_score) : ContextCompat.getColor(context, R.color.incorrect_color_score));
        questionNumber.setText(String.valueOf(position + 1));

        ImageView bookmark = (ImageView) convertView.findViewById(R.id.bookmark);
        bookmark.setVisibility((question.isFlagged()) ? View.VISIBLE : View.INVISIBLE);

        return convertView;
    }

    @Override
    public int getCount() {
        return (questions == null) ? 0 : questions.size();
    }
}
