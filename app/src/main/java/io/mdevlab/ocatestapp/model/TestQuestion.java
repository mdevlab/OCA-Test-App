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
    private String statement;
    private String explanation;
    private boolean isFavorite;
    private RealmList<TestAnswer> answers;
    private boolean isFlagged;

    public TestQuestion() {}

    public TestQuestion(Question question) {
        this.id = question.getId();
        this.type = question.getType();
        this.explanation = question.getExplanation();
        this.isFavorite = question.isFavorite();
        this.statement = question.getStatement();
        this.answers = answersToTestAnswers(question.getAnswers());
        this.isFlagged = false;
    }

    private RealmList<TestAnswer> answersToTestAnswers(RealmList<Answer> answers) {
        RealmList<TestAnswer> testAnswers = new RealmList<>();
        for (Answer answer: answers) {
            testAnswers.add(new TestAnswer(answer));
        }
        return testAnswers;
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

    /**
     * Method that checks whether a question has been answered correctly by the user
     * It loops through the answers of the user until it either finds one that's been answered
     * incorrectly, or until all the answers have been answered correctly.
     *
     * @return whether the question has been answered correctly by the user
     */
    public boolean hasBeenAnsweredCorrectly() {
        for (TestAnswer userAnswer : answers) {
            if (!userAnswer.isCorrect())
                return false;
        }

        return true;
    }
}
