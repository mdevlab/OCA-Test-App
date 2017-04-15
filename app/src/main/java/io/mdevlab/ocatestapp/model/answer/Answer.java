package io.mdevlab.ocatestapp.model.answer;

import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Answer extends RealmObject {

    private int id;
    private String answer;
    private boolean isCorrect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
