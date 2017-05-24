package io.mdevlab.ocatraining.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.TestQuestionAdapter;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.modelManager.TestQuestionManager;
import io.mdevlab.ocatraining.util.Constants;
import io.realm.RealmList;


public class TestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    static String TAG = TestActivity.class.getName();
    private final int MINUTE_TO_SECOND = 60;
    public static String CURRENT_TEST = "test_object";
    private int NUM_ITEMS = Constants.EMPTY_LIST;
    private final int FIRST_ITEM = Constants.EMPTY_LIST;
    private int CURRENT_INDEX = Constants.EMPTY_LIST;
    private ViewPager mTestQuestionViewPager;
    private Button mButtonLast;
    private Button mButtonResult;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private Button mButtonResumeTest;
    private ImageButton mButtonStopTest;
    private FrameLayout mHiddenLayoutTodisableTest;
    private ToggleButton mTogglePauseResumeTest;
    private TextView mTextQuestionspercent;
    private TextView mTextTimer;
    private Button mButtonFirst;
    private RealmList<TestQuestion> mListQuestions;
    private ProgressBar mProgressBarTest;
    private Test mTest;
    private int minute;
    private int second;
    private Thread mTimerThread;
    private int testMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent sentIntent = getIntent();
        testMode = sentIntent.getIntExtra(Constants.TEST_MODE, 1);

        mTestQuestionViewPager = (ViewPager) findViewById(R.id.pager_test_questions);
//        First Last
        mButtonLast = (Button) findViewById(R.id.button_last_question);
        mButtonFirst = (Button) findViewById(R.id.button_first_question);
//        Next Previous
        mButtonNext = (ImageButton) findViewById(R.id.button_next_question);
        mButtonPrevious = (ImageButton) findViewById(R.id.button_previous_question);


//        Results
        mButtonResult = (Button) findViewById(R.id.button_results);
        mButtonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add No comeback to test Activity
                updateCurrentTest();
                buildStopDialog(true, R.string.open_results, R.string.dialog_cancel, R.string.result_enter_message, R.string.result_enter_title);

            }
        });

        //Progress Init
        mTextQuestionspercent = (TextView) findViewById(R.id.text_questions_percent);
        mTextTimer = (TextView) findViewById(R.id.text_test_timer);
        mProgressBarTest = (ProgressBar) findViewById(R.id.progress_test);
        mTogglePauseResumeTest = (ToggleButton) findViewById(R.id.toggle_pause_resume_test);

        mButtonResumeTest = (Button) findViewById(R.id.button_resume_test);
        mHiddenLayoutTodisableTest = (FrameLayout) findViewById(R.id.hidden_layout_to_disable_test);
        mButtonStopTest = (ImageButton) findViewById(R.id.image_button_stop_test);
        mButtonStopTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                buildStopDialog(false, R.string.quit_and_save, R.string.dialog_cancel, R.string.confirm_exit_message, R.string.confirm_exit_title);
            }
        });


        mButtonResumeTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enableTest();
                runTestTimer();
            }
        });

        mTogglePauseResumeTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTimerThread.interrupt();
                    disableTest();
                }
            }
        });

        setNextPreviousListener();
        prepareQuestions();

        NUM_ITEMS = mListQuestions.size();

        if (NUM_ITEMS > Constants.EMPTY_LIST) {
            mProgressBarTest.setMax(NUM_ITEMS);
            initPager();
            initFirstLastButtons();
            updateUi();
        }

        runTestTimer();

    }

    /**
     * This function is for updating the current test before saving it into the db or
     * sending it to the result activity
     */
    private void updateCurrentTest() {
        long timeInSeconds = minute * MINUTE_TO_SECOND + second;
        mTest.setDuration(timeInSeconds);

    }

    /**
     * Prepare the list of question
     * -Get questions from DB
     * -Init Test object depending on the test types:
     * + FINAL_TEST_MODE
     * + CUSTOM_TEST_MODE
     */
    public void prepareQuestions() {

        //TODO get Questions from DB
        switch (testMode) {
            case Constants.FINAL_TEST_MODE:

                mListQuestions = TestQuestionManager.getTestQuestions(Constants.TEST_LIMIT_QUESTIONS);
                mTest = new Test(mListQuestions.size(), Constants.FINAL_TEST_MODE, mListQuestions);
                break;
            case Constants.CUSTOM_TEST_MODE:

                mListQuestions = TestQuestionManager.getTestQuestions(5);
                mTest = new Test(mListQuestions.size(), Constants.CUSTOM_TEST_MODE, mListQuestions);
                break;
        }


    }

    private void buildStopDialog(final Boolean isResult, int positiveButton, int negativeButton, int message, int title) {

        //get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (isResult) {
                    Intent intent = new Intent(TestActivity.this, ResultsActivity.class);
                    intent.putExtra(CURRENT_TEST, mTest);
                    intent.putExtra(Constants.TEST_MODE, testMode);
                    startActivity(intent);
                } else {
                    //TODO save Data in DB
                }
                finish();
            }
        });


        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        //set message and title
        builder.setMessage(getString(message))
                .setTitle(getString(title));

        //Build the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void disableTest() {
        mHiddenLayoutTodisableTest.setVisibility(View.VISIBLE);

    }

    private void enableTest() {
        mHiddenLayoutTodisableTest.setVisibility(View.INVISIBLE);
        mTogglePauseResumeTest.setChecked(false);
    }

    private void runTestTimer() {

        mTimerThread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                incrementTimer();
                                updateTimerUi();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        mTimerThread.start();

    }

    private void updateTimerUi() {
        StringBuilder Timer = new StringBuilder();
        if (minute < 10) {
            Timer.append(Constants.ZERO);
        }
        Timer.append(minute);
        Timer.append(" : ");
        if (second < 10) {
            Timer.append(Constants.ZERO);
        }
        Timer.append(second);
        mTextTimer.setText(Timer.toString());

    }

    private void incrementTimer() {
        if (second == 59) {
            second = 0;
            minute++;
        } else {
            second++;
        }


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

    private void initPager() {
        TestQuestionAdapter testQuestionAdapter = new TestQuestionAdapter(getSupportFragmentManager(), mListQuestions);
        mTestQuestionViewPager.setAdapter(testQuestionAdapter);
        mTestQuestionViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mTimerThread != null)
            mTimerThread.interrupt();
    }

    private void initFirstLastButtons() {
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTestQuestionViewPager.setCurrentItem(FIRST_ITEM, true);
                CURRENT_INDEX = FIRST_ITEM;
            }
        });

        mButtonLast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTestQuestionViewPager.setCurrentItem(NUM_ITEMS, true);
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

    @Override
    public void onBackPressed() {
        buildStopDialog(false, R.string.quit_and_save, R.string.dialog_cancel, R.string.confirm_exit_message, R.string.confirm_exit_title);
    }


}
