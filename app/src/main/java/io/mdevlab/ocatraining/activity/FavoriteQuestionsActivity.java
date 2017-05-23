package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.FavoriteQuestionsAdapter;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.test.QuestionTest;

public class FavoriteQuestionsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_questions);

        setUpToolbar();

        RecyclerView favoriteQuestionsRecyclerView = (RecyclerView) findViewById(R.id.favorite_questions);
        favoriteQuestionsRecyclerView.setAdapter(new FavoriteQuestionsAdapter(FavoriteQuestionsActivity.this, generateDummyFavoriteQuestions()));
        favoriteQuestionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favoriteQuestionsRecyclerView.setLayoutManager(new LinearLayoutManager(FavoriteQuestionsActivity.this));
    }


    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_favorite_questions));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private ArrayList<Question> generateDummyFavoriteQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        String dummyQuestion = getString(R.string.dummy_question);
        for (int i = 0; i < 20; i++)
            questions.add(QuestionTest.createRandomQuestion(1, dummyQuestion));
        return questions;
    }
}
