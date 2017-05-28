package io.mdevlab.ocatraining.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.TestQuestionAdapter;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.mdevlab.ocatraining.modelManager.TestManager;
import io.mdevlab.ocatraining.modelManager.TestQuestionManager;
import io.realm.Realm;
import io.realm.RealmList;

import static io.mdevlab.ocatraining.model.Test.CHAPTER_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.CUSTOM_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.FINAL_TEST_MODE;
import static io.mdevlab.ocatraining.model.Test.TEST_CHAPTER;
import static io.mdevlab.ocatraining.model.Test.TEST_LIMIT_QUESTIONS;
import static io.mdevlab.ocatraining.model.Test.TEST_MODE;


public class TestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    static String TAG = TestActivity.class.getName();
    private final int MINUTES_TO_SECONDS = 60;
    private final int MIllISECONDS_TO_SECONDS = 1000;
    public static final int EMPTY_LIST = 0;
    public static final int ZERO = 0;
    public static String CURRENT_TEST = "test_object";
    private int NUM_ITEMS = EMPTY_LIST;
    private final int FIRST_ITEM = EMPTY_LIST;
    private int CURRENT_INDEX = EMPTY_LIST;
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
    private int currentChapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent sentIntent = getIntent();
        testMode = sentIntent.getIntExtra(TEST_MODE, 1);
        currentChapter = sentIntent.getIntExtra(TEST_CHAPTER, 1);

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

        if (isAnyResumedTestExist()) {
            buildNewResumeDialog(R.string.resume, R.string.new_test, R.string.new_or_resume_message, R.string.new_or_resume_title);
        } else {
            prepareNewTest();
            feedViews();
        }


    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mTimerThread != null)
            mTimerThread.interrupt();
        //update the current Test in case the user leaves the app to another app
        updateCurrentTest();

    }


    /**
     * This function is for updating the current test before saving it into the db or
     * sending it to the result activity
     */
    private void updateCurrentTest() {
        long currentTime = (minute * 60 + second) * 1000;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        mTest.setNumberOfCompletedQuestions(CURRENT_INDEX + 1);
        mTest.setDuration(currentTime);
        realm.commitTransaction();
        realm.close();

    }

    /**
     * Prepare the list of question
     * -Get questions from DB
     * -Init Test object depending on the test types:
     * + FINAL_TEST_MODE
     * + CUSTOM_TEST_MODE
     *
     * @param isResume if resume prepare the resumed test
     */
    public void prepareQuestions(Boolean isResume) {

        if (isResume) {
            prepareResumedTest();
        } else {
            prepareNewTest();
        }

        feedViews();

    }


    private void prepareResumedTest() {
        mTest = TestManager.getLastSavedTest(testMode);
        mListQuestions = mTest.getQuestions();
        prepareResumedUI();
    }

    private void feedViews() {
        NUM_ITEMS = mListQuestions.size();
        if (NUM_ITEMS > EMPTY_LIST) {
            mProgressBarTest.setMax(NUM_ITEMS);
            initPager();
            initFirstLastButtons();
            updateUi();
            runTestTimer();
        }


    }

    private void prepareNewTest() {
        switch (testMode) {
            case FINAL_TEST_MODE:
                mListQuestions = TestQuestionManager.getTestQuestions(TEST_LIMIT_QUESTIONS);
                mTest = new Test(mListQuestions.size(), FINAL_TEST_MODE, mListQuestions);
                TestManager.cleanUnfinishedTest(FINAL_TEST_MODE);
                break;
            case CUSTOM_TEST_MODE:
                mListQuestions = TestQuestionManager.getTestQuestions(5);
                mTest = new Test(mListQuestions.size(), CUSTOM_TEST_MODE, mListQuestions);
                TestManager.cleanUnfinishedTest(CUSTOM_TEST_MODE);
                break;
            case CHAPTER_TEST_MODE:
                mListQuestions = TestQuestionManager.getChapterTestQuestions(5,currentChapter);
                mTest = new Test(mListQuestions.size(), CHAPTER_TEST_MODE, mListQuestions);
                TestManager.cleanUnfinishedTest(CHAPTER_TEST_MODE);
                break;

        }
        mTest = TestManager.createTest(mTest);
    }

    /**
     * This function gets data from resumed Test and feed it to the current Test activity
     */
    private void prepareResumedUI() {
        long currentResumedTestDuration = mTest.getDuration();
        minute = (int) ((currentResumedTestDuration / (MIllISECONDS_TO_SECONDS * MINUTES_TO_SECONDS)) % MINUTES_TO_SECONDS);
        second = (int) (currentResumedTestDuration / MIllISECONDS_TO_SECONDS) % MINUTES_TO_SECONDS;
        mTestQuestionViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTestQuestionViewPager.setCurrentItem(mTest.getNumberOfCompletedQuestions() - 1);
            }
        }, 500);


    }


    private void disableTest() {
        mHiddenLayoutTodisableTest.setVisibility(View.VISIBLE);

    }

    private void enableTest() {
        mHiddenLayoutTodisableTest.setVisibility(View.INVISIBLE);
        mTogglePauseResumeTest.setChecked(false);
    }

    /**
     * Timer for the Test  11 minutes :12 secondes
     * use the function incrementTimer to increment
     * and updateTimerUi to update the UI
     */
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

    /**
     * update the Timer called by timer Thread
     */
    private void updateTimerUi() {
        StringBuilder Timer = new StringBuilder();
        if (minute < 10) {
            Timer.append(ZERO);
        }
        Timer.append(minute);
        Timer.append(" : ");
        if (second < 10) {
            Timer.append(ZERO);
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

    /**
     * Update the progress ui
     */
    private void updateUi() {
        StringBuilder percentStringBuilder = new StringBuilder(String.valueOf(CURRENT_INDEX + 1));
        percentStringBuilder.append("/");
        percentStringBuilder.append(String.valueOf(NUM_ITEMS));
        mTextQuestionspercent.setText(percentStringBuilder.toString());
        mProgressBarTest.setProgress(CURRENT_INDEX + 1);

    }

    /**
     * Check if the current page is the last
     * purpose set the appropriate UI
     */
    public void checkIfLastPage() {
        int currentItem = CURRENT_INDEX + 1;
        if (currentItem == NUM_ITEMS) {
            prepareForResults();
        } else {
            removeResultUi();
        }

    }

    private void removeResultUi() {
        mButtonLast.setVisibility(View.VISIBLE);
        mButtonResult.setVisibility(View.INVISIBLE);
    }

    private void prepareForResults() {
        mButtonLast.setVisibility(View.INVISIBLE);
        mButtonResult.setVisibility(View.VISIBLE);
    }


    /**
     * this dialog issued when the user want to stop the exam
     *
     * @param isResult
     * @param positiveButton
     * @param negativeButton
     * @param message
     * @param title
     */
    private void buildStopDialog(final Boolean isResult, int positiveButton, int negativeButton, int message, int title) {

        //get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (isResult) {
                    Intent intent = new Intent(TestActivity.this, ResultsActivity.class);
                    intent.putExtra(CURRENT_TEST, mTest);
                    intent.putExtra(TEST_MODE, testMode);
                    startActivity(intent);
                    TestManager.updateFinishedTest(mTest);

                } else {

                    updateCurrentTest();
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

    /**
     * this dialog issued when the user want to resume the exam
     *
     * @param positiveButton
     * @param negativeButton
     * @param message
     * @param title
     */
    private void buildNewResumeDialog(int positiveButton, int negativeButton, int message, int title) {

        //get the builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                prepareQuestions(true);
                Toast.makeText(TestActivity.this, "Resume", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                prepareQuestions(false);
                Toast.makeText(TestActivity.this, "New", Toast.LENGTH_SHORT).show();

            }
        });

        //set message and title
        builder.setMessage(getString(message))
                .setTitle(getString(title));

        //Build the dialog
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void onBackPressed() {

        buildStopDialog(false, R.string.quit_and_save, R.string.dialog_cancel, R.string.confirm_exit_message, R.string.confirm_exit_title);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        CURRENT_INDEX = position;
        updateUi();
        checkIfLastPage();
        updateCurrentTest();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * check if we have any resumed Tests
     *
     * @return
     */
    public boolean isAnyResumedTestExist() {
        if (TestManager.getLastSavedTest(testMode) == null)
            return false;
        return true;
    }
}
