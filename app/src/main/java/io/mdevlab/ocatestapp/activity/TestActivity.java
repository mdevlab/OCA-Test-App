package io.mdevlab.ocatestapp.activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.adapter.TestQuestionAdapter;
import io.mdevlab.ocatestapp.model.Test;
import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.test.ChapterTest;
import io.mdevlab.ocatestapp.util.Constants;

public class TestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    static String TAG = TestActivity.class.getName();
    private int NUM_ITEMS = Constants.EMPTY_LIST;
    private final int FIRST_ITEM = Constants.EMPTY_LIST;
    private int CURRENT_INDEX = Constants.EMPTY_LIST;
    private ViewPager mTestQuestionViewPager;
    private Button mButtonLast;
    private Button mButtonResult;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private TextView mTextQuestionspercent;
    private Button mButtonFirst;
    private List<TestQuestion> mListQuestions;
    private ProgressBar mProgressBarTest;
    private Test mTest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTestQuestionViewPager = (ViewPager) findViewById(R.id.pager_test_questions);
//        First Last
        mButtonLast = (Button) findViewById(R.id.button_last_question);
        mButtonFirst = (Button) findViewById(R.id.button_first_question);
//        Next Previous
        mButtonNext = (ImageButton) findViewById(R.id.button_next_question);


        mButtonPrevious = (ImageButton) findViewById(R.id.button_previous_question);


        setNextPreviousListener();

//        Results
        mButtonResult = (Button) findViewById(R.id.button_results);

        mTextQuestionspercent = (TextView) findViewById(R.id.text_questions_percent);
        mProgressBarTest = (ProgressBar) findViewById(R.id.progress_test);

        prepareQuestions();

        NUM_ITEMS = mListQuestions.size();

        if (NUM_ITEMS > Constants.EMPTY_LIST) {
            mProgressBarTest.setMax(NUM_ITEMS);
            initpager();
            initFirstLastButtons();
            updateUi();
        }


    }

    public void prepareQuestions() {
        mListQuestions = ChapterTest.createListTestQuestion(this);
    }



    private void setNextPreviousListener() {

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CURRENT_INDEX < NUM_ITEMS) {
                    mTestQuestionViewPager.setCurrentItem(CURRENT_INDEX + 1);
                }
            }
        });
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (CURRENT_INDEX > 0) {
                    mTestQuestionViewPager.setCurrentItem(CURRENT_INDEX - 1);
                }
            }
        });

    }

    private void initpager() {
        TestQuestionAdapter testQuestionAdapter = new TestQuestionAdapter(getSupportFragmentManager(), mListQuestions);
        mTestQuestionViewPager.setAdapter(testQuestionAdapter);
        mTestQuestionViewPager.addOnPageChangeListener(this);
    }

    private void initFirstLastButtons() {
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTestQuestionViewPager.setCurrentItem(FIRST_ITEM,true);
                CURRENT_INDEX = FIRST_ITEM;
            }
        });

        mButtonLast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTestQuestionViewPager.setCurrentItem(NUM_ITEMS,true);
                CURRENT_INDEX = NUM_ITEMS - 1;
            }
        });

    }


    private void updateUi() {
        StringBuilder percentStringBuilder = new StringBuilder(String.valueOf(CURRENT_INDEX + 1));
        percentStringBuilder.append("/");
        percentStringBuilder.append(String.valueOf(NUM_ITEMS));
        mTextQuestionspercent.setText(percentStringBuilder.toString());
        mProgressBarTest.setProgress(CURRENT_INDEX + 1);


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        CURRENT_INDEX = position;
        updateUi();
        checkIfLastPage();
        Log.d(TAG, "position : " + position);
        Log.d(TAG, "CURRENT_INDEX : " + CURRENT_INDEX);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void checkIfLastPage() {
        int currentItem = CURRENT_INDEX + 1;
        if (currentItem == NUM_ITEMS) {
            prepareForResults();
        } else {
            revoveResultUi();
        }


    }

    private void revoveResultUi() {
        mButtonLast.setVisibility(View.VISIBLE);
        mButtonResult.setVisibility(View.INVISIBLE);
    }

    private void prepareForResults() {
        mButtonLast.setVisibility(View.INVISIBLE);
        mButtonResult.setVisibility(View.VISIBLE);

    }


}
