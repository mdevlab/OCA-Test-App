package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.test.TestQuestionTest;
import io.mdevlab.ocatraining.util.Constants;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        TestQuestion question = TestQuestionTest.createRandomTestQuestion(0, ResponseActivity.this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.question_container, QuestionFragment.newInstance(question, true));

        ft.commit();

        int questionId = -1;
        if (getIntent().getExtras() != null)
            questionId = getIntent().getExtras().getInt(Constants.QUESTION_ID);
    }
}
