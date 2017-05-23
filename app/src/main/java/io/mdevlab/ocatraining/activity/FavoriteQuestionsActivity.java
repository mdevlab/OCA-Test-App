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
import io.mdevlab.ocatraining.modelManager.QuestionManager;

public class FavoriteQuestionsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_questions);

        setUpToolbar();
        setUpFavoriteQuestions();
    }


    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_favorite_questions));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setUpFavoriteQuestions() {
        RecyclerView favoriteQuestionsRecyclerView = (RecyclerView) findViewById(R.id.favorite_questions);
        favoriteQuestionsRecyclerView.setAdapter(new FavoriteQuestionsAdapter(FavoriteQuestionsActivity.this, getFavoriteQuestions()));
        favoriteQuestionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favoriteQuestionsRecyclerView.setLayoutManager(new LinearLayoutManager(FavoriteQuestionsActivity.this));
    }


    private ArrayList<Question> getFavoriteQuestions() {
        return QuestionManager.getFavoriteQuestions();
    }
}
