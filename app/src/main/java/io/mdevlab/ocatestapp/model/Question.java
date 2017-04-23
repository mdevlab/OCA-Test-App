package io.mdevlab.ocatestapp.model;

import io.mdevlab.ocatestapp.util.Constants;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Question extends RealmObject {

    public static final String ID_COLUMN = "id";

    private int id;
    /*
    Either single answer question or multi answer question
    Both are constants and are defined in this class
     */
    private int type;
    private String statement;
    private String explanation;
    private boolean isFavorite;
    private RealmList<Answer> answers;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

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

    private String getTypeName() {
        return (type == Constants.SINGLE_ANSWER_QUESTION) ? "single answer question" : "multiple answer question";
    }
}
