package io.mdevlab.ocatraining.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Question extends RealmObject {

    public static final String ID_COLUMN = "id";
    public static final String IS_FAVORITE_COLUMN = "isFavorite";

    // Question id
    private int id;

    /**
     * Either single answer question or multi answer question
     * Both are constants and are defined in the Constants class
     */
    private int type;

    // Question statement (concrete question)
    private String statement;

    // Question answer explanation
    private String explanation;

    // Whether the question has been saved or not
    private boolean isFavorite;

    // List of given answers for this question
    private RealmList<Answer> answers;

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

    public RealmList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<Answer> answers) {
        this.answers = answers;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", type=" + type +
                ", explanation='" + explanation + '\'' +
                ", isFavorite=" + isFavorite +
                ", answers=" + answers.size() +
                '}';
    }
}
