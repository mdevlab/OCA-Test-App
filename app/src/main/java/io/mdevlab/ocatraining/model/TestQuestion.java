package io.mdevlab.ocatraining.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestQuestion extends RealmObject implements Parcelable {

    public static final String ID_COLUMN = "id";

    // Test question id
    private int id;

    /**
     * Either single answer question or multi answer question
     * Both are constants and are defined in the Constants class
     */
    private int type;

    // Test question explanation
    private String explanation;

    // Test question statement
    private String statement;

    // Whether the current question is saved or not
    private boolean isFavorite;

    /**
     * Whether the current test question is flagged
     * Flagged = Bookmarked to be saved and reviewed at the end of the test
     */
    private boolean isFlagged;

    // List of answers to the test question
    private RealmList<TestAnswer> answers;

    // Chapter id
    private int chapterId;

    public TestQuestion() {
    }

    public TestQuestion(Question question) {
        this.id = question.getId();
        this.type = question.getType();
        this.explanation = question.getExplanation();
        this.isFavorite = question.isFavorite();
        this.statement = question.getStatement();
        this.answers = answersToTestAnswers(question.getAnswers());
        this.isFlagged = false;
        this.chapterId =question.getChapterId();
    }

    /**
     * Constructor for Parcelable
     * purpose : Send Realm object via intents
     * @param in
     */
    protected TestQuestion(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        explanation = in.readString();
        statement = in.readString();
        isFavorite = in.readByte() != 0;
        isFlagged = in.readByte() != 0;
        Parcelable[] parcelableArray =
                in.readParcelableArray(TestAnswer.class.getClassLoader());
        TestAnswer[] resultArray = null;
        if (parcelableArray != null) {
            resultArray = Arrays.copyOf(parcelableArray, parcelableArray.length, TestAnswer[].class);
            answers = new RealmList<>();
            for (TestAnswer answer : resultArray) {
                answers.add(answer);
            }
        }

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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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
     * @param answers List of answer objects to be converted
     * @return Converted list of Answer objects to list of Test Answer objects
     */
    private RealmList<TestAnswer> answersToTestAnswers(RealmList<Answer> answers) {
        RealmList<TestAnswer> testAnswers = new RealmList<>();
        for (Answer answer : answers) {
            testAnswers.add(new TestAnswer(answer));
        }
        return testAnswers;
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
            if (!userAnswer.isAnswerCorrect())
                return false;
        }

        return true;
    }

    /**
     * This function is for setting all current answers to false
     */
    public void clearAnswers() {
        for (TestAnswer answer : answers) {
            answer.setSelected(false);
        }

    }

    public static final Creator<TestQuestion> CREATOR = new Creator<TestQuestion>() {
        @Override
        public TestQuestion createFromParcel(Parcel in) {
            return new TestQuestion(in);
        }

        @Override
        public TestQuestion[] newArray(int size) {
            return new TestQuestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(explanation);
        dest.writeString(statement);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeByte((byte) (isFlagged ? 1 : 0));
        Parcelable[] pAnswers = new Parcelable[answers.size()];
        for (int i = 0; i < answers.size(); i++) {
            pAnswers[i] = answers.get(i);
        }
        dest.writeParcelableArray(pAnswers, flags);

    }
}
