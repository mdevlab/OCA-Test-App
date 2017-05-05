package io.mdevlab.ocatraining.model;

import io.mdevlab.ocatraining.modelManager.AnswerManager;
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

    public TestAnswer() {
    }

    public TestAnswer(Answer answer) {
        this.id = answer.getId();
        this.answer = answer.getAnswer();
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

    @Override
    public String toString() {
        return "TestAnswer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    /**
     * Method that checks whether the current answer object has been answered correctly by the user
     * It compares the user's answer with the original answer.
     *
     * @return whether the current answer object has been answered correctly by the user
     */
    public boolean isCorrect() {
        Answer originalAnswer = AnswerManager.getAnswerById(id);

        if (originalAnswer == null)
            return true;

        /**
         * If the answer is correct and the user selected it -> Correct
         * If the answer is incorrect and the user didn't select it -> Correct
         * otherwise -> Incorrect
         */
        return isSelected == originalAnswer.isCorrect();
    }
}