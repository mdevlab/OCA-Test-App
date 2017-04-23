package io.mdevlab.ocatestapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.adapter.ResultsAdapter;
import io.mdevlab.ocatestapp.model.Test;
import io.mdevlab.ocatestapp.util.Constants;

public class ResultsActivity extends AppCompatActivity {

    TextView score;
    TextView duration;
    GridView questions;
    ResultsAdapter adapter;
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        score = (TextView) findViewById(R.id.score);
        duration = (TextView) findViewById(R.id.duration);
        questions = (GridView) findViewById(R.id.questions_grid);

        // For testing
//        io.mdevlab.ocatestapp.test.Test.runChapterJsonToRealmTest(ResultsActivity.this);
//        test = io.mdevlab.ocatestapp.test.Test.createTest();

        if (test != null) {
            score.setText(test.getScore());
            duration.setText(test.getDurationToDisplay());

            // Questions grid adapter
            adapter = new ResultsAdapter(ResultsActivity.this,
                    R.layout.item_result,
                    test.getQuestionsAsArrayList());
            questions.setAdapter(adapter);

            // Onclick event handling for questions grid
            questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent response = new Intent(ResultsActivity.this, ResponseActivity.class);
                    response.putExtra(Constants.QUESTION_ID, (adapter.getItem(position) != null) ? adapter.getItem(position).getId() : -1);
                    startActivity(response);
                }
            });
        }
    }

    public void backToMainScreen(View view) {
        Intent mainScreen = new Intent(ResultsActivity.this, MainActivity.class);
        startActivity(mainScreen);
    }

    public void takeAnotherTest(View view) {
        // Take another test
    }

    public void upgrade(View view) {
        // Upgrade
    }
}
