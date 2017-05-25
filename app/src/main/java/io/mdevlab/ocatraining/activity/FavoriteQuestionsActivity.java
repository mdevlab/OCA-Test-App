package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.FavoriteQuestionsAdapter;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.modelManager.QuestionManager;

public class FavoriteQuestionsActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_questions);
        setUpToolbar(getString(R.string.title_activity_favorite_questions));
        setUpFavoriteQuestions();
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
