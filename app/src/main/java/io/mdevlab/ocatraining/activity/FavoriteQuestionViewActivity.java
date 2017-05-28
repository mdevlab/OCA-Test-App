package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.modelManager.QuestionManager;

/**
 * Created by husaynhakeem on 5/28/17.
 */

public class FavoriteQuestionViewActivity extends SingleQuestionTestActivity {

    public static final String CURRENT_QUESTION = "current question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar(getString(R.string.title_activity_favorite_questions_view));
        removeScores();
    }


    @Override
    protected void setUpViews() {
        super.setUpViews();
        mNextAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowNext) {
                    onBackPressed();
                } else {
                    showQuestionAnswer();
                    isShowNext = true;
                }
            }
        });
    }


    /**
     * Removes the scores layout (Containing the number of correct and incorrect answers)
     */
    private void removeScores() {
        mScores.setVisibility(View.GONE);
    }


    @Override
    public void showQuestionAnswer() {
        super.showQuestionAnswer();
        mCurrentFragment.verifyQuestionAnswer();
    }


    @Override
    protected void setUpCurrentQuestion() {
        setQuestionUi();
        Question question = QuestionManager.getQuestionById(getIntent().getExtras().getInt(CURRENT_QUESTION));
        currentQuestion = new TestQuestion(question);
    }


    @Override
    protected void setAnswerUi() {
        mNextAnswerButton.setText(R.string.back_to_favorite_questions);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.question_answer));
    }


    @Override
    public void onBackPressed() {
        Intent favoriteQuestions = new Intent(FavoriteQuestionViewActivity.this, FavoriteQuestionsActivity.class);
        startActivity(favoriteQuestions);
        finish();
    }
}
