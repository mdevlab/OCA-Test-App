package io.mdevlab.ocatraining.model;

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


    /**
     * Return if the current answers is correct
     * @return  true = correct / false = incorrect
     */
    public boolean isCorrect() {
        return isCorrect;
    }


    /**
     * Check if the user answer is correct
     * means if the user select the answers and iscorrect is true the answer is correct
     * Example :
     * isCorrect = true  isSelected = true   the return is true  correct
     * isCorrect = true  isSelected = false   the return is false  incorrect
     *
     * @return true false
     */
    public boolean isAnswerCorrect() {
        return isCorrect == isSelected;
    }
}
