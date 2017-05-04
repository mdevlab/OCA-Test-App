package io.mdevlab.ocatestapp.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.adapter.TestQuestionAdapter;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.test.ChapterTest;

public class TestActivity extends AppCompatActivity {

    private ViewPager mTestQuestionViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTestQuestionViewPager = (ViewPager) findViewById(R.id.test_questions_viewpager);

        List<TestQuestion> listQuestion = ChapterTest.createListTestQuestion(this);
        TestQuestionAdapter testQuestionAdapter = new TestQuestionAdapter(getSupportFragmentManager(),listQuestion);
        mTestQuestionViewPager.setAdapter(testQuestionAdapter);


    }




    public void nextFragement() {
        //        TestQuestion question = createRandomTestQuestion(0,TestActivity.this);
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.question_container, QuestionFragment.newInstance(question,false));
//
//        ft.commit();
    }

    public void PreviousFragement() {

    }
}
