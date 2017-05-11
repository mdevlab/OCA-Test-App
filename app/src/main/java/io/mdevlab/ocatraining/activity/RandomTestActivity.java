package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.test.TestQuestionTest;


public class RandomTestActivity extends AppCompatActivity {


    private Button mNextAnswerButton;

    private boolean isShowNext = false;
    private TestQuestion currentQuestion;
    private QuestionFragment mCurrentFragmen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        initActivity();

    }


    /**
     * initial the random activity Question with a random question
     * and initialize the fragment
     */
    private void initActivity() {

        currentQuestion = TestQuestionTest.createRandomTestQuestion(0, RandomTestActivity.this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mCurrentFragmen = QuestionFragment.newInstance(currentQuestion, false, true);
        ft.add(R.id.question_answer_container, mCurrentFragmen);
        ft.commit();
    }

    /**
     * This function get a new random question
     * and display it
     */
    public void showNextQuestion() {



    }

    /**
     * This function is for rendering the answer of the current question
     * show the explanation and show the effective answer
     */
    public void showQuestionAnswer() {

        mCurrentFragmen.verifyQuestionAnswer();



    }
}
