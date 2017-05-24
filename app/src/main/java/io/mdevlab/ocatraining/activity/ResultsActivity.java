package io.mdevlab.ocatraining.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.ResultsAdapter;
import io.mdevlab.ocatraining.dialog.DialogNewTest;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.util.Constants;
import io.mdevlab.ocatraining.util.UtilActions;

public class ResultsActivity extends AppCompatActivity {

    private static final String DIALOG_NEW_TEST_TAG = "new test dialog";

    private TextView mScore;
    private TextView mDuration;
    private GridView mQuestions;
    private ResultsAdapter mAdapter;
    private Test test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mScore = (TextView) findViewById(R.id.score);
        mDuration = (TextView) findViewById(R.id.duration);
        mQuestions = (GridView) findViewById(R.id.questions_grid);

        // For testing
//        io.mdevlab.ocatraining.test.Test.populateDataBase(ResultsActivity.this);
//        test = io.mdevlab.ocatraining.test.TestTest.createTest();

        Intent intent = getIntent();
        if (intent.hasExtra(TestActivity.CURRENT_TEST))
            test = (Test) intent.getParcelableExtra(TestActivity.CURRENT_TEST);

        if (test != null) {
            mScore.setText(getString(R.string.test_score) + test.getScore());
            mDuration.setText(getString(R.string.test_duration) + test.getDurationToDisplay());

            // Questions grid adapter
            mAdapter = new ResultsAdapter(ResultsActivity.this,
                    R.layout.item_result,
                    test.getQuestionsAsArrayList());
            mQuestions.setAdapter(mAdapter);

            // Onclick event handling for questions grid
            mQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent response = new Intent(ResultsActivity.this, ResponseActivity.class);
                    response.putExtra(Constants.CURRENT_ANSWER, mAdapter.getItem(position));
                    response.putExtra(Constants.ANSWER_NUMBER, String.valueOf(position + 1));
                    startActivity(response);
                }
            });
        }
    }


    public void backToMainScreen(View view) {
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


    public void takeAnotherTest(View view) {
        int testMode = getCurrentTestMode();
        DialogFragment newTestDialog = DialogNewTest.newInstance(testMode);
        newTestDialog.show(getSupportFragmentManager(), DIALOG_NEW_TEST_TAG);
    }


    private int getCurrentTestMode() {
        if (getIntent() != null &&
                getIntent().getExtras() != null &&
                getIntent().getExtras().containsKey(Constants.TEST_MODE))
            return getIntent().getExtras().getInt(Constants.TEST_MODE);

        // Default value
        return Constants.FINAL_TEST_MODE;
    }


    public void upgrade(View view) {
        UtilActions.displayUpgradeDialog(ResultsActivity.this);
    }


    @Override
    public void onBackPressed() {
        buildLeaveDialog();

    }
}
