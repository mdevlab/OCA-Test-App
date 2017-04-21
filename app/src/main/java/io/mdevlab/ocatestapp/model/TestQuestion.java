package io.mdevlab.ocatestapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestQuestion extends RealmObject {

    public static final String ID_COLUMN = "id";

    private int id;
    /*
    Either single answer question or multi answer question
    Both are constants and are defined in this class
     */
    private int type;
    private String explanation;
    private boolean isFavorite;
    private RealmList<TestAnswer> answers;
    private boolean isFlagged;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public RealmList<TestAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<TestAnswer> answers) {
        this.answers = answers;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    @Override
    public String toString() {
        return "TestQuestion{" +
                "id=" + id +
                ", type=" + type +
                ", explanation='" + explanation + '\'' +
                ", isFavorite=" + isFavorite +
                ", answers=" + answers.size() +
                ", isFlagged=" + isFlagged +
                '}';
    }
}
