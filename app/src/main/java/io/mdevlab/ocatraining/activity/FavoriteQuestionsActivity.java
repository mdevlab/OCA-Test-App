package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.FavoriteQuestionsAdapter;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.modelManager.QuestionManager;
import io.realm.RealmResults;

public class FavoriteQuestionsActivity extends ActivityBase {


    View noFavorites;
    RealmResults<Question> favoriteQuestions;
    FavoriteQuestionsAdapter questionsAdapter;
    RecyclerView questionsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_questions);
        setUpToolbar(getString(R.string.title_activity_favorite_questions));
        setUpDFP();
        initializeViews();
    }


    private void initializeViews() {
        noFavorites = findViewById(R.id.no_favorites);
        questionsRecyclerView = (RecyclerView) findViewById(R.id.favorite_questions);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpViews();
    }


    private void setUpViews() {

        favoriteQuestions = QuestionManager.getFavoriteQuestions();

        if (favoriteQuestions == null || favoriteQuestions.size() == 0) {
            setUpNoFavoritesView();
        } else {
            setUpAdapter();
            setUpRecyclerView();
        }
    }


    private void setUpNoFavoritesView() {
        showNoFavoritesLayout(true);
    }


    private void setUpAdapter() {
        questionsAdapter = new FavoriteQuestionsAdapter(FavoriteQuestionsActivity.this, favoriteQuestions);
    }


    private void setUpRecyclerView() {
        questionsRecyclerView.setAdapter(questionsAdapter);
        questionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        questionsRecyclerView.addItemDecoration(new DividerItemDecoration(questionsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(FavoriteQuestionsActivity.this));
        showNoFavoritesLayout(false);
    }


    private void showNoFavoritesLayout(boolean favoritesIsEmpty) {
        if (noFavorites != null) {
            noFavorites.setVisibility(favoritesIsEmpty ? View.VISIBLE : View.GONE);
        }

        if (questionsRecyclerView != null) {
            questionsRecyclerView.setVisibility(favoritesIsEmpty ? View.GONE : View.VISIBLE);
        }
    }
}
