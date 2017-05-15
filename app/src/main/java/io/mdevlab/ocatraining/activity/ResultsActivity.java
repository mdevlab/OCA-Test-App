package io.mdevlab.ocatraining.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.ResultsAdapter;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.util.Constants;

import static io.mdevlab.ocatraining.R.string.test_score;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        score = (TextView) findViewById(R.id.score);
        duration = (TextView) findViewById(R.id.duration);
        questions = (GridView) findViewById(R.id.questions_grid);

        // For testing
//        io.mdevlab.ocatraining.test.Test.populateDataBase(ResultsActivity.this);
//        test = io.mdevlab.ocatraining.test.TestTest.createTest();

        Intent intent = getIntent();
        if (intent.hasExtra(TestActivity.CURRENT_TEST))
            test = (Test) intent.getParcelableExtra(TestActivity.CURRENT_TEST);

        if (test != null) {
            score.setText(getString(R.string.test_score)+test.getScore());
            duration.setText(getString(R.string.test_duration)+test.getDurationToDisplay());

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
        buildLeaveDialog();
    }

    public void takeAnotherTest(View view) {
        // Take another test
    }

    public void upgrade(View view) {
        // Upgrade
    }

    @Override
    public void onBackPressed() {
        buildLeaveDialog();

    }

    private void buildLeaveDialog() {

        //get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultsActivity.this);
        builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent mainScreen = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(mainScreen);
            }
        });


        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        //set message and title
        builder.setMessage(getString(R.string.result_exit_message))
                .setTitle(getString(R.string.result_exit_title));

        //Build the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
