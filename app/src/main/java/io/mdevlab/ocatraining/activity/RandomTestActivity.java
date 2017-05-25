package io.mdevlab.ocatraining.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.modelManager.TestQuestionManager;


public class RandomTestActivity extends ActivityBase {

    private TestQuestion currentQuestion;

    private QuestionFragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    private TextView mCorrectScore;
    private TextView mIncorrectScore;
    private Button mNextAnswerButton;

    private boolean isShowNext = false;

    private int mCorrectAnswers;
    private int mInCorrectAnswers;
    private int mQuestionNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_test);
        setUpToolbar(getString(R.string.random_test));
        setUpViews();
        setUpActivity();
    }


    private void setUpViews() {
        mCorrectScore = (TextView) findViewById(R.id.correct_score);
        mIncorrectScore = (TextView) findViewById(R.id.incorrect_score);

        mNextAnswerButton = (Button) findViewById(R.id.button_next_answer);
        mNextAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowNext) {
                    showNextQuestion();
                    isShowNext = false;
                } else {
                    showQuestionAnswer();
                    isShowNext = true;
                }

            }
        });
    }


    /**
     * initial the random activity Question with a random question
     * and initialize the fragment
     */
    private void setUpActivity() {
        generateRandomQuestion();
        setUpRandomQuestionFragment();
    }


    private void generateRandomQuestion() {
        mQuestionNumber++;
        setQuestionUi();
        currentQuestion = TestQuestionManager.getRandomQuestion();
    }


    private void setUpRandomQuestionFragment() {
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, false, true);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.question_answer_container, mCurrentFragment)
                .commit();
    }


    /**
     * This function get a new random question
     * and display it
     */
    public void showNextQuestion() {

        //Increment the counter of Questions
        mQuestionNumber++;
        setQuestionUi();

        //TODO  make sure to get a random question from the DB each time
        currentQuestion = TestQuestionManager.getRandomQuestion();

        //TODO set the fragement Transition
        // TODO down => up new question
        // TODO left => right answer
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        mCurrentFragment = QuestionFragment.newInstance(currentQuestion, false, true);
        ft.replace(R.id.question_answer_container, mCurrentFragment);
        ft.commit();


    }


    /**
     * set the question UI
     */
    private void setQuestionUi() {

        mNextAnswerButton.setText(R.string.question_answer);
        getSupportActionBar().setTitle("Question : " + mQuestionNumber);

    }


    private void setAnswerUi() {

        mNextAnswerButton.setText(R.string.next_question);
        getSupportActionBar().setTitle("Answer For Question : " + mQuestionNumber);
    }


    /**
     * This function is for rendering the answer of the current question
     * show the explanation and show the effective answer
     */
    public void showQuestionAnswer() {
        setAnswerUi();
        Boolean isCorrect = mCurrentFragment.verifyQuestionAnswer();
        updateScore(isCorrect);

    }


    /**
     * update the score if true it means the user's answer was correct other ways false
     * true increment Correct score
     * false increment inCorrect score
     *
     * @param isCorrect true/false
     */
    private void updateScore(Boolean isCorrect) {
        if (isCorrect) {
            mCorrectAnswers++;
            mCorrectScore.setText(String.valueOf(mCorrectAnswers));
        } else {
            mInCorrectAnswers++;
            mIncorrectScore.setText(String.valueOf(mInCorrectAnswers));
        }
    }


    @Override
    public void onBackPressed() {
        buildStopDialog();
    }


    private void buildStopDialog() {

        //Get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(RandomTestActivity.this);
        builder.setPositiveButton(R.string.quit, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        //set message and title
        builder.setMessage(getString(R.string.confirm_exit_random_question_message))
                .setTitle(getString(R.string.confirm_exit_random_question_title));

        //Build the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
