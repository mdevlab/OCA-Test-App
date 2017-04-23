package io.mdevlab.ocatestapp.activitie;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.mdevlab.ocatestapp.QuestionFragment;
import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.test.ChapterTest;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        TestQuestion question = ChapterTest.createRandomTestQuestion(0,ResponseActivity.this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.question_container, QuestionFragment.newInstance(question,true));

        ft.commit();
    }
}
