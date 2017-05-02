package io.mdevlab.ocatestapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatestapp.fragment.QuestionFragment;
import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.test.TestQuestionTest;
import io.mdevlab.ocatestapp.util.Constants;

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
