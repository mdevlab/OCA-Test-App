package io.mdevlab.ocatraining.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.mdevlab.ocatraining.BuildConfig;
import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.Test;
import io.mdevlab.ocatraining.modelManager.TestManager;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

import static io.mdevlab.ocatraining.model.Test.TEST_CHAPTER;
import static io.mdevlab.ocatraining.model.Test.TEST_MODE;
import static io.mdevlab.ocatraining.util.LocalTimeUtil.getDate;


public class TestChartActivity extends ActivityBase {

    private int mCurrentTestMode;
    private int mCurrentTestChapter;
    private List<Test> mCurrentTestList;
    private ColumnChartView mTestChartView;
    private ColumnChartData data;
    private TextView mAverageScoreTextView;
    private TextView mAverageScorePercentTextView;
    private TextView mAverageTimeTextView;
    public static final String CHAPTER_DASHBOARD = "chapter_dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);
        setUpToolbar(getString(R.string.title_activity_final_test_chart));
        mAverageScoreTextView = (TextView) findViewById(R.id.average_score_text_view);
        mAverageScorePercentTextView = (TextView) findViewById(R.id.average_score_percent_text_view);
        mAverageTimeTextView = (TextView) findViewById(R.id.average_time_text_view);
        mTestChartView = (ColumnChartView) findViewById(R.id.tests_chart);


        setUpChartSettings();
        getIntentData();
        getTestListAndSetupTheView();


    }

    private void getIntentData() {
        if (getIntent().getExtras().containsKey(TEST_MODE))
            mCurrentTestMode = getIntent().getIntExtra(TEST_MODE, 1);

        if (mCurrentTestMode == Test.CHAPTER_TEST_MODE) {
            if (getIntent().getExtras().containsKey(TEST_CHAPTER)) {
                mCurrentTestChapter = getIntent().getIntExtra(TEST_CHAPTER, 0);
                mToolbar.setTitle(getIntent().getStringExtra(CHAPTER_DASHBOARD));
            }
        }
    }

    private void setUpChartSettings() {
        mTestChartView.setValueSelectionEnabled(false);
        mTestChartView.setZoomType(ZoomType.HORIZONTAL);
        mTestChartView.setOnValueTouchListener(new ValueTouchListener());
    }

    private void updateAveragesData() {
        int currentAverageScore = getAverageScore();
        int questionLimit = (mCurrentTestMode == Test.FINAL_TEST_MODE) ? BuildConfig.FINAL_TEST_QUESTIONS_LIMIT : BuildConfig.CUSTOM_TEST_QUESTIONS_LIMIT;
        int currentPercentAverageScore = ((currentAverageScore) * 100) / questionLimit;
        long currentAverageDuration = getAverageDuration();
        mAverageScoreTextView.setText(currentAverageScore + " / " + questionLimit);
        mAverageScorePercentTextView.setText(currentPercentAverageScore + "%");
        if (currentAverageDuration > 0)
            mAverageTimeTextView.setText(Test.getDurationHoursMinuteToDisplay(TestChartActivity.this, currentAverageDuration));

    }

    private void updateChartData() {
        int numColumns = mCurrentTestList.size();

        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            int score = mCurrentTestList.get(i).getNumberOfCorrectAnswers();
            values.add(new SubcolumnValue((float) score, ChartUtils.pickColor()));

            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
        }

        data = new ColumnChartData(columns);
        setUpAxis();

        mTestChartView.setColumnChartData(data);
        mTestChartView.startDataAnimation();

    }

    private void setUpAxis() {
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Tests");
        axisY.setName("Score");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
    }

    public void getTestListAndSetupTheView() {
        feedTestList();
        updateChartData();
        updateAveragesData();

    }

    public void feedTestList() {
        switch (mCurrentTestMode) {
            case Test.FINAL_TEST_MODE:
            case Test.CUSTOM_TEST_MODE:
                mCurrentTestList = TestManager.getAllFinishedFinalTests(mCurrentTestMode);
                break;
            case Test.CHAPTER_TEST_MODE:
                mCurrentTestList = TestManager.getAllFinishedCustomTestsByChapter(mCurrentTestChapter);
                break;
        }

        if (mCurrentTestList.size() <= 1) {
            Toast.makeText(this, "To get Beautiful Dashboards take more tests ", Toast.LENGTH_LONG).show();
        }


    }

    public int getAverageScore() {

        int averageScore = 0;
        if (mCurrentTestList.size() > 0) {

            for (Test test : mCurrentTestList) {
                averageScore += test.getNumberOfCorrectAnswers();
            }
            averageScore = averageScore / mCurrentTestList.size();
        }
        return averageScore;
    }

    public long getAverageDuration() {

        int averageDuration = 0;
        if (mCurrentTestList.size() > 0) {

            for (Test test : mCurrentTestList) {
                averageDuration += test.getDuration();
            }
            averageDuration = averageDuration / mCurrentTestList.size();
        }

        return averageDuration;
    }


    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {


            Toast.makeText(TestChartActivity.this, "Test finished the : " + getDate(mCurrentTestList.get(columnIndex).getFinishTime(), "dd/MM/yyyy hh:mm:ss"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {


        }

    }

}
