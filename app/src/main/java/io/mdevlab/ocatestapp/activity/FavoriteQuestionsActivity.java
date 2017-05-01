package io.mdevlab.ocatestapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.mdevlab.ocatestapp.R;
import io.mdevlab.ocatestapp.adapter.FavoriteQuestionsAdapter;
import io.mdevlab.ocatestapp.model.Question;
import io.mdevlab.ocatestapp.test.QuestionTest;

public class FavoriteQuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_questions);

        RecyclerView favoriteQuestionsRecyclerView = (RecyclerView) findViewById(R.id.favorite_questions);
        favoriteQuestionsRecyclerView.setAdapter(new FavoriteQuestionsAdapter(FavoriteQuestionsActivity.this, generateDummyFavoriteQuestions()));
        favoriteQuestionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favoriteQuestionsRecyclerView.setLayoutManager(new LinearLayoutManager(FavoriteQuestionsActivity.this));
    }

    private ArrayList<Question> generateDummyFavoriteQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        String dummyQuestion = getString(R.string.dummy_question);
        for (int i = 0; i < 20; i++)
            questions.add(QuestionTest.createRandomQuestion(1, dummyQuestion));
        return questions;
    }
}
