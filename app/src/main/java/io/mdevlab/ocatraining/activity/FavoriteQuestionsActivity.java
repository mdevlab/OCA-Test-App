package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.adapter.FavoriteQuestionsAdapter;
import io.mdevlab.ocatraining.model.Question;
import io.mdevlab.ocatraining.modelManager.QuestionManager;
import io.realm.RealmResults;

public class FavoriteQuestionsActivity extends ActivityBase {


    RealmResults<Question> favoriteQuestions;
    FavoriteQuestionsAdapter questionsAdapter;
    RecyclerView questionsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_questions);
        setUpToolbar(getString(R.string.title_activity_favorite_questions));
        setUpDFP(null);
        setUpViews();
    }


    private void setUpViews() {
        setUpAdapter();
        setUpRecyclerView();
    }


    private void setUpAdapter() {
        favoriteQuestions = QuestionManager.getFavoriteQuestions();
        questionsAdapter = new FavoriteQuestionsAdapter(FavoriteQuestionsActivity.this, favoriteQuestions);
    }


    private void setUpRecyclerView() {
        questionsRecyclerView = (RecyclerView) findViewById(R.id.favorite_questions);
        questionsRecyclerView.setAdapter(questionsAdapter);
        questionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        questionsRecyclerView.addItemDecoration(new DividerItemDecoration(questionsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(FavoriteQuestionsActivity.this));
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateFavoriteQuestions();
    }


    private void updateFavoriteQuestions() {
        favoriteQuestions = QuestionManager.getFavoriteQuestions();
        Log.e("Saved questions", "Size is: " + favoriteQuestions.size());
        questionsAdapter.setQuestions(favoriteQuestions);
        questionsAdapter.notifyDataSetChanged();
    }
}
