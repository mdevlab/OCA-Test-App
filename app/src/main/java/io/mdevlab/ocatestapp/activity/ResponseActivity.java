package io.mdevlab.ocatestapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.util.Constants;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        int questionId = -1;
        if (getIntent().getExtras() != null)
            questionId = getIntent().getExtras().getInt(Constants.QUESTION_ID);
    }
}
