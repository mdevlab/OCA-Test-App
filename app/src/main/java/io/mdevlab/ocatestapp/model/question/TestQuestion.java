package io.mdevlab.ocatestapp.model.question;

import io.mdevlab.ocatestapp.model.answer.TestAnswer;
import io.realm.RealmList;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class TestQuestion extends BaseQuestion {

    private RealmList<TestAnswer> answers;
    private boolean isFlagged;

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
}
