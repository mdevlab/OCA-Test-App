package io.mdevlab.ocatestapp.test;

import android.util.Log;

import io.mdevlab.ocatestapp.dao.AnswerManager;
import io.mdevlab.ocatestapp.dao.ChapterManager;
import io.mdevlab.ocatestapp.dao.QuestionManager;
import io.mdevlab.ocatestapp.model.Answer;
import io.mdevlab.ocatestapp.model.Chapter;
import io.mdevlab.ocatestapp.model.Question;
import io.mdevlab.ocatestapp.util.Constants;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by husaynhakeem on 4/21/17.
 */

public class ChapterTest {

    private static final String TAG = ChapterTest.class.getSimpleName();

    public static void deleteChapters() {
        ChapterManager.deleteChapters();
    }

    public static void readChapters() {
        RealmResults<Chapter> chapters = ChapterManager.getAllChapters();
        for (Chapter chapter : chapters)
            Log.e(TAG, chapter.toString());
    }

    private static Chapter createChapter(int index) {
        Chapter randomChapter = new Chapter();
        randomChapter.setId(ChapterManager.getNextIndex());
        randomChapter.setName("Chapter " + index);
        randomChapter.setSummary("Summary " + index);
        randomChapter.setQuestions(createRandomQuestions(index));
        return randomChapter;
    }

    private static RealmList<Question> createRandomQuestions(int index) {
        RealmList<Question> questions = new RealmList<>();
        questions.add(createRandomQuestion(index));
        questions.add(createRandomQuestion(index));
        return questions;
    }

    private static Question createRandomQuestion(int index) {
        Question randomQuestion = new Question();
        randomQuestion.setId(QuestionManager.getNextIndex());
        randomQuestion.setType(Constants.SINGLE_ANSWER_QUESTION);
        randomQuestion.setExplanation("Explanation " + index);
        randomQuestion.setFavorite(true);
        randomQuestion.setAnswers(createRandomAnswers(index));
        return randomQuestion;
    }

    private static RealmList<Answer> createRandomAnswers(int index) {
        RealmList<Answer> answers = new RealmList<>();
        answers.add(createRandomAnswer(index));
        answers.add(createRandomAnswer(index));
        return answers;
    }

    private static Answer createRandomAnswer(int index) {
        Answer randomAnswer = new Answer();
        randomAnswer.setId(AnswerManager.getNextIndex());
        randomAnswer.setAnswer("Answer " + index);
        randomAnswer.setCorrect(true);
        return randomAnswer;
    }

    private static void generateChapter() {
        ChapterManager.createChapter(createChapter(200));
    }

    public static void runAllTest() {
        deleteChapters();
        generateChapter();
        readChapters();
    }
}
