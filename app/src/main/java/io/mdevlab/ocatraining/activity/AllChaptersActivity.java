package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.util.Constants;

public class AllChaptersActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chapters);
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
        switch (view.getId()) {
            case R.id.final_test:
                testMode = Constants.FINAL_TEST_MODE;
                break;
            case R.id.custom_test:
                testMode = Constants.CUSTOM_TEST_MODE;
                break;
            case R.id.random_test:
                testMode = Constants.RANDOM_TEST_MODE;
                break;
        }
        Intent beginTest = new Intent(AllChaptersActivity.this, null);
        beginTest.putExtra(Constants.TEST_MODE, testMode);
        startActivity(beginTest);
    }
}
