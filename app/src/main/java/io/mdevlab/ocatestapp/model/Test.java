package io.mdevlab.ocatestapp.model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Test extends RealmObject {

    public static final String ID_COLUMN = "id";

    // Test id
    private int id;

    // Test duration in seconds
    private long duration;

    // Number of completed questions in the test
    private int numberOfCompletedQuestions;

    // Total number of questions in the test
    private int totalNumberOfQuestions;

    /**
     * Either final test or customized test
     * Both are constants and are defined in the Constants class
     */
    private int type;

    // List of questions in the test
    private RealmList<TestQuestion> questions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getNumberOfCompletedQuestions() {
        return numberOfCompletedQuestions;
    }

    public void setNumberOfCompletedQuestions(int numberOfCompletedQuestions) {
        this.numberOfCompletedQuestions = numberOfCompletedQuestions;
    }

    public int getTotalNumberOfQuestions() {
        return totalNumberOfQuestions;
    }

    public void setTotalNumberOfQuestions(int totalNumberOfQuestions) {
        this.totalNumberOfQuestions = totalNumberOfQuestions;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RealmList<TestQuestion> getQuestions() {
        return questions;
    }

    public ArrayList<TestQuestion> getQuestionsAsArrayList() {
        ArrayList<TestQuestion> questionsAsArrayList = new ArrayList<>();
        if (questions != null)
            for (TestQuestion question : questions) {
                questionsAsArrayList.add(question);
            }
        return questionsAsArrayList;
    }

    public void setQuestions(RealmList<TestQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", duration=" + duration +
                ", progress=" + numberOfCompletedQuestions + "/" + totalNumberOfQuestions +
                ", type=" + type +
                ", questions=" + questions.size() +
                '}';
    }

    /**
     * Method that loops through the completed questions from the current test object
     * It checks whether each question has been answered correctly and hence calculates
     * the final score.
     *
     * @return final score in the format numberOfCorrectAnswers/totalNumberOfQuestions
     */
    public String getScore() {
        int numberOfCorrectAnswers = 0;
        if (questions != null)
            for (int i = 0; i < numberOfCompletedQuestions; i++) {
                if (questions.get(i).hasBeenAnsweredCorrectly())
                    numberOfCorrectAnswers++;
            }
        return numberOfCorrectAnswers + "/" + totalNumberOfQuestions;
    }

    /**
     * Method that calculates and formats the test duration for display
     *
     * @return The test duration as "36 minutes" or "2 hours, 23 minutes" format
     * depending on the value of hours (when it's 0, it isn't displayed)
     */
    public String getDurationToDisplay() {
        int hours = (int) (duration / 3600);
        int minutes = (int) ((duration / 3600) % 60);

        if (hours == 0)
            return minutes + " minutes";
        return hours + " hours, " + minutes + " minutes";
    }
}
