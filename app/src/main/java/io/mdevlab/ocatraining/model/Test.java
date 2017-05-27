package io.mdevlab.ocatraining.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Test extends RealmObject implements Parcelable {

    public static final String ID_COLUMN = "id";
    public static final String TEST_MODE = "test mode";
    public static final int FINAL_TEST_MODE = 1;
    public static final int CUSTOM_TEST_MODE = 2;

    public static final int RANDOM_TEST_MODE = 3;
    public static final int TEST_LIMIT_QUESTIONS = 70;

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

    public Test() {
    }

    public Test(int totalNumberOfQuestions, int type, RealmList<TestQuestion> questions) {
        this.totalNumberOfQuestions = totalNumberOfQuestions;
        this.type = type;
        this.questions = questions;
    }

    protected Test(Parcel in) {
        id = in.readInt();
        duration = in.readLong();
        numberOfCompletedQuestions = in.readInt();
        totalNumberOfQuestions = in.readInt();
        type = in.readInt();
        Parcelable[] parcelableArray =
                in.readParcelableArray(TestQuestion.class.getClassLoader());
        TestQuestion[] resultArray = null;

        if (parcelableArray != null) {
            resultArray = Arrays.copyOf(parcelableArray, parcelableArray.length, TestQuestion[].class);
            questions = new RealmList<>();
            for (TestQuestion testQuestion : resultArray) {
                questions.add(testQuestion);
            }
        }
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

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
        int minutes = (int) ((duration % 3600) / 60);

        if (hours == 0)
            return minutes + " minutes";
        return hours + " hours, " + minutes + " minutes";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(duration);
        dest.writeInt(numberOfCompletedQuestions);
        dest.writeInt(totalNumberOfQuestions);
        dest.writeInt(type);
        Parcelable[] pQuestions = new Parcelable[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            pQuestions[i] = questions.get(i);
        }
        dest.writeParcelableArray(pQuestions, flags);

    }
}
