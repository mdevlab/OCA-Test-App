package io.mdevlab.ocatestapp.model.question;

import io.mdevlab.ocatestapp.model.answer.Answer;
import io.realm.RealmList;

/**
 * Created by husaynhakeem on 4/16/17.
 */

public class Question extends BaseQuestion {

    private RealmList<Answer> answers;

    public RealmList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(RealmList<Answer> answers) {
        this.answers = answers;
    }
}
