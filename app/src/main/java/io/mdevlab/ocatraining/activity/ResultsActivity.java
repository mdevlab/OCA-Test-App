package io.mdevlab.ocatraining.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

public class ResultsActivity extends ActivityBase {

    private static final String DIALOG_NEW_TEST_TAG = "new test dialog";
    private ResultsAdapter mAdapter;
    private Test mTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setUpToolbar(getString(R.string.title_result));
        setUpViews();
        setUpTest();

        // For testing
//        io.mdevlab.ocatraining.test.Test.populateDataBase(ResultsActivity.this);
//        test = io.mdevlab.ocatraining.test.TestTest.createTest();
    }


    private void setUpTest() {
        Intent intent = getIntent();
        if (intent.hasExtra(TestActivity.CURRENT_TEST))
            mTest = (Test) intent.getParcelableExtra(TestActivity.CURRENT_TEST);
    }


    private void setUpViews() {
        if (mTest != null) {
            setUpTestScore();
            setUpTestDuration();
            setUpQuestionsAdapter();
            setUpQuestionsList();
        }
    }


    private void setUpTestScore() {
        TextView mScore = (TextView) findViewById(R.id.score);
        mScore.setText(getString(R.string.test_score) + mTest.getScore());
    }


    private void setUpTestDuration() {
        TextView mDuration = (TextView) findViewById(R.id.duration);
        mDuration.setText(getString(R.string.test_duration) + mTest.getDurationToDisplay());
    }


    private void setUpQuestionsAdapter() {
        mAdapter = new ResultsAdapter(ResultsActivity.this,
                R.layout.item_result,
                mTest.getQuestionsAsArrayList());
    }


    private void setUpQuestionsList() {
        GridView mQuestions = (GridView) findViewById(R.id.questions_grid);
        mQuestions.setAdapter(mAdapter);
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


    public void backToMainScreen(View view) {
        buildLeaveDialog();
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
