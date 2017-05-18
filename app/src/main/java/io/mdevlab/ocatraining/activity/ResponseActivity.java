package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.util.Constants;


public class ResponseActivity extends AppCompatActivity {


    private TestQuestion currentQuestion;
    private String currentQuestionNumber;
    private QuestionFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.ANSWER_NUMBER) && intent.hasExtra(Constants.CURRENT_ANSWER)) {
            currentQuestion = intent.getParcelableExtra(Constants.CURRENT_ANSWER);
            currentQuestionNumber = intent.getStringExtra(Constants.ANSWER_NUMBER);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mCurrentFragment = QuestionFragment.newInstance(currentQuestion, true, false);
            ft.replace(R.id.test_answer_container, mCurrentFragment);
            ft.commit();


        }

    }

    private void updateToolbar() {
        getSupportActionBar().setTitle("Question : " + currentQuestionNumber);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentFragment.verifyQuestionAnswer();
        updateToolbar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
