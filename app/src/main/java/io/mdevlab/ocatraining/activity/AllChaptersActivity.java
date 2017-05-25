package io.mdevlab.ocatraining.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.util.Constants;

public class AllChaptersActivity extends ActivityBase
        implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chapters);
        setUpToolbar(getString(R.string.title_all_chapters));
        setUpViews();
    }


    private void setUpViews() {
        LinearLayout mFinalTest = (LinearLayout) findViewById(R.id.final_test);
        mFinalTest.setOnClickListener(this);
        LinearLayout mCustomTest = (LinearLayout) findViewById(R.id.custom_test);
        mCustomTest.setOnClickListener(this);
        LinearLayout mRandomQuestions = (LinearLayout) findViewById(R.id.random_test);
        mRandomQuestions.setOnClickListener(this);
    }


    /**
     * The UI contains 3 main clickable sections:
     * - Final test
     * - Custom test
     * - Random test
     * Depending on which on is clicked, a specific value is passed on to
     * the Test handler class.
     *
     * @param view: Clicked view
     */
    @Override
    public void onClick(View view) {
        int testMode = Constants.FINAL_TEST_MODE;
        Class<? extends Activity> testActivityClass = null;
        switch (view.getId()) {
            case R.id.final_test:
                testMode = Constants.FINAL_TEST_MODE;
                testActivityClass = TestActivity.class;
                break;
            case R.id.custom_test:
                testMode = Constants.CUSTOM_TEST_MODE;
                testActivityClass = TestActivity.class;
                break;
            case R.id.random_test:
                testMode = Constants.RANDOM_TEST_MODE;
                testActivityClass = RandomTestActivity.class;
                break;
        }
        if (testActivityClass != null) {
            Intent beginTest = new Intent(AllChaptersActivity.this, testActivityClass);
            beginTest.putExtra(Constants.TEST_MODE, testMode);
            startActivity(beginTest);
        }


    }
}
