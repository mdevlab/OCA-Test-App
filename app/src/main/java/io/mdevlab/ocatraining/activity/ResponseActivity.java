package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.util.Constants;


public class ResponseActivity extends ActivityBase {

    final String TAG = ResponseActivity.class.getSimpleName();
    private String mCurrentQuestionNumber;
    private QuestionFragment mCurrentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        setUpToolbar("");
        setUpResponseScreen();
    }


    private void setUpResponseScreen() {
        Intent intent = getIntent();
        if (intent != null &&
                intent.hasExtra(Constants.ANSWER_NUMBER) &&
                intent.hasExtra(Constants.CURRENT_ANSWER)) {
            setUpCurrentQuestion(intent);
            setUpResponseFragment(intent);
        }
    }


    private void setUpCurrentQuestion(Intent intent) {
        mCurrentQuestionNumber = intent.getStringExtra(Constants.ANSWER_NUMBER);
    }


    private void setUpResponseFragment(Intent intent) {
        TestQuestion currentQuestion = intent.getParcelableExtra(Constants.CURRENT_ANSWER);
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, true, false);
        getSupportFragmentManager().beginTransaction().replace(R.id.test_answer_container, mCurrentFragment).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentFragment.verifyQuestionAnswer();
        updateToolbar();
    }


    private void updateToolbar() {
        try {
            getSupportActionBar().setTitle("Question : " + mCurrentQuestionNumber);
        } catch (NullPointerException e) {
            Log.e(TAG, "setTilte method on null action bar. Error: " + e.getMessage());
        }
    }
}