package io.mdevlab.ocatraining.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatraining.fragment.QuestionFragment;
import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.TestQuestion;

import static io.mdevlab.ocatraining.test.TestQuestionTest.createRandomTestQuestion;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TestQuestion question = createRandomTestQuestion(0,TestActivity.this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.question_container, QuestionFragment.newInstance(question,false));

        ft.commit();
    }
}
