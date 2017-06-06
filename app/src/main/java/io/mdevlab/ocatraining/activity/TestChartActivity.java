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

import static io.mdevlab.ocatraining.model.Test.TEST_MODE;


public class TestChartActivity extends ActivityBase {

    private String mCurrentTestmode;
    private List<Test> mCurrentTestList;
    private ColumnChartView mTestChartView;
    private ColumnChartData data;
    private TextView mAverageScoreTextview;
    private TextView mAverageScorePercentTextview;
    private TextView mAverageTimeTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chart);
        setUpToolbar(getString(R.string.title_activity_final_test_chart));
        mAverageScoreTextview = (TextView) findViewById(R.id.average_score_text_view);
        mAverageScorePercentTextview = (TextView) findViewById(R.id.average_score_percent_text_view);
        mAverageTimeTextview = (TextView) findViewById(R.id.average_time_text_view);
        mTestChartView = (ColumnChartView) findViewById(R.id.tests_chart);
        setUpChartSettings();


        if (getIntent().getExtras().containsKey(TEST_MODE))
            mCurrentTestmode = getIntent().getStringExtra(TEST_MODE);

        getTestListAndSetupTheView(5);


    }

    private void setUpChartSettings() {
        mTestChartView.setValueSelectionEnabled(false);
        mTestChartView.setZoomType(ZoomType.HORIZONTAL);
        mTestChartView.setOnValueTouchListener(new ValueTouchListener());
    }

    private void updateAveragesData() {
        int currentAverageScore = getAverageScore();
        int currentPercentAverageScore = ((currentAverageScore) * 100) / BuildConfig.FINAL_TEST_QUESTIONS_LIMIT;
        long currentAverageDuration = getAverageDuration();
        mAverageScoreTextview.setText(currentAverageScore + "");
        mAverageScorePercentTextview.setText(currentPercentAverageScore + "");
        mAverageTimeTextview.setText(String.valueOf(currentAverageDuration));

    }

    private void updateChartData() {
        int numColumns = mCurrentTestList.size();

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
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


    public void getTestListAndSetupTheView(int limit) {
        mCurrentTestList = TestManager.getAllTests();
        updateChartData();
        updateAveragesData();

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
            Toast.makeText(TestChartActivity.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {


        }

    }

}
