package io.mdevlab.ocatestapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import io.mdevlab.ocatestapp.model.TestQuestion;
import io.mdevlab.ocatestapp.util.Constants;

/**
 * Created by bachiri on 4/23/17.
 */
//TODO Bachiri set the listener for Checkbox and Radiobox answers  inside createMultipleAnswerContainer and  createSingleAnswerContainer

//TODO fill data from mquestion Radio box and .. function  setResponseData

public class QuestionFragment extends Fragment {

    private TestQuestion mQuestion;
    public static final int CHECKBOX_ID_COMPLEMENTARY = 1000;
    public static final int RADIOBUTTON_ID_COMPLEMENTARY = 2000;
    private LinearLayout mAnswersContainer;
    private RadioGroup mAnswersRadioGroup;
    private TextView mQuestionStatement;
    private TextView mAnswerExplanation;
    private TextView mAnswerTitle;
    private ToggleButton isFlagged;
    private ToggleButton isFavorite;
    private Boolean isResponse;

    public static final QuestionFragment newInstance(TestQuestion question, Boolean isReponse) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.mQuestion = question;
        questionFragment.isResponse = isReponse;
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

        //check if this view is for the response
        if (isResponse) {
            setTheViewResponse();
            setResponseData();
        }

        //Type of allowed Question
        if (mQuestion != null) {
            if (mQuestion.getType() == Constants.SINGLE_ANSWER_QUESTION) {
                createSingleAnswerContainer();

            } else if (mQuestion.getType() == Constants.MULTIPLE_ANSWER_QUESTION) {
                createMultipleAnswerContainer();
            }
        }

        return v;
    }

    /**
     * This function adjust response view
     */
    private void setTheViewResponse() {
        isFlagged.setVisibility(View.INVISIBLE);
        mAnswerExplanation.setVisibility(View.VISIBLE);
        mAnswerTitle.setVisibility(View.VISIBLE);

    }

    /**
     *
     * Set data from the question object
     */
    private void setResponseData() {

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
            checkBox.setId(i + CHECKBOX_ID_COMPLEMENTARY);
            checkBox.setText(" " + mQuestion.getAnswers().get(i).getAnswer());
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
            radioButton.setId(i + RADIOBUTTON_ID_COMPLEMENTARY);
            radioButton.setText(" " + mQuestion.getAnswers().get(i).getAnswer());
            radioButton.setLayoutParams(lparams);
            mAnswersRadioGroup.addView(radioButton);
        }


    }
}
