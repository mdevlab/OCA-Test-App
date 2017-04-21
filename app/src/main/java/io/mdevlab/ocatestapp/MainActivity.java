package io.mdevlab.ocatestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.mdevlab.ocatestapp.model.Question;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
