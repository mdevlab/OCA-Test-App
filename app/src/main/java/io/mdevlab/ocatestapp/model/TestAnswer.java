package io.mdevlab.ocatestapp.model;

import io.mdevlab.ocatestapp.modelManager.AnswerManager;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestAnswer extends RealmObject {

    public static final String ID_COLUMN = "id";

    // Test answer id
    private int id;

    // Test answer statement
    private String answer;

    // Whether this answer has been selected or not
    private boolean isSelected;

    // Whether this answer has been selected or not
    private boolean isCorrect;

    public TestAnswer() {
    }

    public TestAnswer(Answer answer) {
        this.id = answer.getId();
        this.answer = answer.getAnswer();
        this.isCorrect = answer.isCorrect();
        this.isSelected = false;

    }

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }


    @Override
    public String toString() {
        return "TestAnswer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", isSelected=" + isSelected +
                ", isCorrect=" + isCorrect +
                '}';
    }

    public boolean isCorrect() {
        return isCorrect;
    }
    public boolean isAnswerCorrect() {
        return isCorrect == isSelected;
    }
}
