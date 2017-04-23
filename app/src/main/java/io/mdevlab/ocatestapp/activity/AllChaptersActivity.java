package io.mdevlab.ocatestapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.util.Constants;

public class AllChaptersActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chapters);
    }

    @Override
    public void onClick(View view) {
        int testMode = Constants.ALL_CHAPTERS_FINAL_TEST;
        switch (view.getId()) {
            case R.id.final_test:
                testMode = Constants.ALL_CHAPTERS_FINAL_TEST;
            case R.id.custom_test:
                testMode = Constants.ALL_CHAPTERS_CUSTOM_TEST;
            case R.id.random_test:
                testMode = Constants.ALL_CHAPTERS_RANDOM_TEST;
        }
        Intent beginTest = new Intent(AllChaptersActivity.this, null);
        beginTest.putExtra(Constants.ALL_CHAPTERS_TEST_MODE, testMode);
        startActivity(beginTest);
    }
}
