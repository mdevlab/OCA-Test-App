package io.mdevlab.ocatestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.mdevlab.ocatestapp.model.question.Question;
import io.mdevlab.ocatestapp.util.Constants;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question= realm.createObject(Question.class);
                question.setId(1);
                question.setFavorite(true);
                question.setType(Constants.SINGLE_ANSWER_QUESTION);
                question.setExplanation("Meh..");
            }
        });

        Log.d(TAG, String.valueOf(question.getId()));
        Log.d(TAG, String.valueOf(question.isFavorite()));
        Log.d(TAG, String.valueOf(question.getType()));
        Log.d(TAG, question.getExplanation());
        Log.d(TAG, String.valueOf(question.getAnswers().size()));
    }
}
