package io.mdevlab.ocatraining.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.TestQuestion;
import io.realm.Realm;

import static io.mdevlab.ocatraining.model.Question.MULTIPLE_ANSWER_QUESTION;
import static io.mdevlab.ocatraining.model.Question.SINGLE_ANSWER_QUESTION;

/**
 * Created by bachiri on 4/23/17.
 */


public class QuestionFragment extends Fragment {

    private static Boolean isRandomQuestion;
    private TestQuestion mQuestion;
    public static final int ID_COMPLEMENTARY = 1000;
    private LinearLayout mAnswersContainer;
    private RadioGroup mAnswersRadioGroup;
    private TextView mQuestionStatement;
    private TextView mAnswerExplanation;
    private TextView mAnswerTitle;
    private ToggleButton isFlagged;
    private ToggleButton isFavorite;
    private Boolean isResponse;
    private int questionType;


    /**
     * @param question   The question to render within this fragement
     * @param isResponse is the response is true prepare the fragment to render a response other ways the question.
     * @return
     */
    public static final QuestionFragment newInstance(TestQuestion question, Boolean isResponse, Boolean isRandomQuestion) {
        QuestionFragment.isRandomQuestion = isRandomQuestion;
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.mQuestion = question;
        questionFragment.isResponse = isResponse;
        return questionFragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.item_question, container, false);
        //Get the views objects
        mAnswersContainer = (LinearLayout) v.findViewById(R.id.answers_container);
        mAnswersRadioGroup = (RadioGroup) v.findViewById(R.id.answers_radio_group);
        mQuestionStatement = (TextView) v.findViewById(R.id.question_statement);
        mAnswerExplanation = (TextView) v.findViewById(R.id.answer_explanation);
        mAnswerTitle = (TextView) v.findViewById(R.id.response_title);
        isFlagged = (ToggleButton) v.findViewById(R.id.flag_question);
        isFavorite = (ToggleButton) v.findViewById(R.id.save_question);
        bindViewFromQuestion();
        setFavoriteFlaggedListeners();

        //If the request of creating the fragment is coming from the random section
        if (isRandomQuestion) {
            setRandomQuestionUI();
        }


        //Type of allowed Question
        if (mQuestion != null) {
            if (mQuestion.getType() == SINGLE_ANSWER_QUESTION) {
                createSingleAnswerContainer();
                questionType = SINGLE_ANSWER_QUESTION;

            } else if (mQuestion.getType() == MULTIPLE_ANSWER_QUESTION) {
                createMultipleAnswerContainer();
                questionType = MULTIPLE_ANSWER_QUESTION;
            }
        }

        //check if this view is for the response
        if (isResponse) {
            setTheViewResponse();
        }

        return v;
    }

    /**
     * Set and prepare the ui for the random question
     * hide/show appropriate Ui component
     */
    private void setRandomQuestionUI() {
        isFlagged.setVisibility(View.INVISIBLE);
    }

    /**
     * The favorite and flag
     * favorite: to save the question as a favorite
     * flag : to flag the current question (used to let the user see the flagged questions if he doubt about the given answer )
     */
    private void setFavoriteFlaggedListeners() {
        isFlagged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                mQuestion.setFlagged(isChecked);
                realm.commitTransaction();


            }
        });

        isFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                mQuestion.setFavorite(isChecked);
                //QuestionManager.updateQuestionFavoriteById(mQuestion.getId(), isChecked);
                realm.commitTransaction();
            }
        });
    }

    /**
     * This function adjust response view
     */
    private void setTheViewResponse() {
        isFlagged.setVisibility(View.INVISIBLE);
        mAnswerExplanation.setVisibility(View.VISIBLE);
        mAnswerTitle.setText(R.string.question_answer);
        disableCompoundButtons();
        setAnswerData();
    }

    /**
     * Disable all Checkbox/RadioButton that hold answers
     * to prevent the user from playing and check/uncheck the Compound Button
     */
    private void disableCompoundButtons() {
        for (int i = 0; i < mQuestion.getAnswers().size(); i++) {
            CompoundButton compoundButton = (CompoundButton) mAnswersContainer.findViewById(i + ID_COMPLEMENTARY);
            compoundButton.setEnabled(false);
        }
    }

    /**
     * Set data from the question object
     */
    private void setAnswerData() {

        mAnswerExplanation.setText(mQuestion.getExplanation());

    }

    /**
     * fill the data from the Question object
     */
    private void bindViewFromQuestion() {
        mQuestionStatement.setText(mQuestion.getStatement());
        isFavorite.setChecked(mQuestion.isFavorite());
        isFlagged.setChecked(mQuestion.isFlagged());

    }

    /**
     * This function is for the multiple answer container   CheckBox
     * Create dynamic answers container based on the specified Type
     * SINGLE_ANSWER_QUESTION or  MULTIPLE_ANSWER_QUESTION
     */
    private void createMultipleAnswerContainer() {

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int checkboxCount = mQuestion.getAnswers().size();

        for (int i = 0; i < checkboxCount; i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setId(i + ID_COMPLEMENTARY);
            checkBox.setText(" " + mQuestion.getAnswers().get(i).getAnswer());
            checkBox.setChecked(mQuestion.getAnswers().get(i).isSelected());
            final int index = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    mQuestion.getAnswers().get(index).setSelected(isChecked);
                    realm.commitTransaction();
                }
            });
            checkBox.setLayoutParams(lparams);
            mAnswersContainer.addView(checkBox);
        }
    }

    /**
     * This function is for the single answer container RadioButton
     * Create dynamic answers container based on the specified Type
     * SINGLE_ANSWER_QUESTION or  MULTIPLE_ANSWER_QUESTION
     */
    private void createSingleAnswerContainer() {

        mAnswersRadioGroup.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int radioButtonCount = mQuestion.getAnswers().size();

        for (int i = 0; i < radioButtonCount; i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setId(i + ID_COMPLEMENTARY);
            radioButton.setText(" " + mQuestion.getAnswers().get(i).getAnswer());
            radioButton.setChecked(mQuestion.getAnswers().get(i).isSelected());
            final int index = i;
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    mQuestion.getAnswers().get(index).setSelected(isChecked);
                    realm.commitTransaction();

                }
            });
            radioButton.setLayoutParams(lparams);
            mAnswersRadioGroup.addView(radioButton);
        }


    }

    /**
     * This function is for verifying the answers
     * and setting the appropriate UI
     * <p>
     * true  all answers are correct
     * false one or more answers are false or maybe all answers are false
     *
     * @return
     */
    public Boolean verifyQuestionAnswer() {

        for (int i = 0; i < mQuestion.getAnswers().size(); i++) {

            CompoundButton compoundButton = (CompoundButton) mAnswersContainer.findViewById(i + ID_COMPLEMENTARY);
            if (mQuestion.getAnswers().get(i).isAnswerCorrect()) {
                if (mQuestion.getAnswers().get(i).isCorrect() == true) {
                    compoundButton.setTextColor(Color.GREEN);
                }
            } else if (!mQuestion.getAnswers().get(i).isAnswerCorrect()) {
                if (mQuestion.getAnswers().get(i).isSelected() == true) {
                    compoundButton.setTextColor(Color.RED);
                }
                if (mQuestion.getAnswers().get(i).isCorrect() == true) {
                    compoundButton.setTextColor(Color.GREEN);
                }
            }
        }

        setTheViewResponse();
        return mQuestion.hasBeenAnsweredCorrectly();
    }
}
