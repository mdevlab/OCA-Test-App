package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;


public class SingleQuestionTestActivity extends ActivityBase {

    protected TestQuestion currentQuestion;

    protected QuestionFragment mCurrentFragment;
    protected FragmentManager mFragmentManager;

    protected View mScores;
    protected TextView mCorrectScore;
    protected TextView mIncorrectScore;
    protected Button mNextAnswerButton;

    protected boolean isShowNext = false;

    protected int mCorrectAnswers;
    protected int mInCorrectAnswers;
    protected int mQuestionNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_test);
        setUpToolbar(getString(R.string.title_activity_random_test));
        setUpViews();
        setUpActivity();
    }


    protected void setUpViews() {
        mScores = findViewById(R.id.scores_layout);
        mCorrectScore = (TextView) findViewById(R.id.correct_score);
        mIncorrectScore = (TextView) findViewById(R.id.incorrect_score);
        mNextAnswerButton = (Button) findViewById(R.id.button_next_answer);
    }


    /**
     * initial the random activity Question with a random question
     * and initialize the fragment
     */
    private void setUpActivity() {
        setUpCurrentQuestion();
        setUpQuestionFragment();
    }


    protected void setUpCurrentQuestion() {
    }


    private void setUpQuestionFragment() {
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, false, true);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.question_answer_container, mCurrentFragment)
                .commit();
    }


    /**
     * set the question UI
     */
    protected void setQuestionUi() {
        mNextAnswerButton.setText(R.string.question_answer);
    }


    protected void setAnswerUi() {
    }


    /**
     * This function is for rendering the answer of the current question
     * show the explanation and show the effective answer
     */
    public void showQuestionAnswer() {
        setAnswerUi();
    }
}
