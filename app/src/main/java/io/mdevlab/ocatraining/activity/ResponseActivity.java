package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;

import static io.mdevlab.ocatraining.model.Answer.ANSWER_NUMBER;
import static io.mdevlab.ocatraining.model.Answer.CURRENT_ANSWER;


public class ResponseActivity extends ActivityBase {

    final String TAG = ResponseActivity.class.getSimpleName();
    private String mCurrentQuestionNumber;
    private QuestionFragment mCurrentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        setUpToolbar("");
        setUpDFP(null);
        setUpResponseScreen();
    }


    private void setUpResponseScreen() {
        Intent intent = getIntent();
        if (intent != null &&
                intent.hasExtra(ANSWER_NUMBER) &&
                intent.hasExtra(CURRENT_ANSWER)) {
            setUpCurrentQuestion(intent);
            setUpResponseFragment(intent);
        }
    }


    private void setUpCurrentQuestion(Intent intent) {
        mCurrentQuestionNumber = intent.getStringExtra(ANSWER_NUMBER);
    }


    private void setUpResponseFragment(Intent intent) {
        TestQuestion currentQuestion = intent.getParcelableExtra(CURRENT_ANSWER);
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