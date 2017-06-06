package io.mdevlab.ocatraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import io.mdevlab.ocatraining.R;
import io.mdevlab.ocatraining.model.Test;

import static io.mdevlab.ocatraining.model.Test.TEST_MODE;


public class DashboardActivity extends ActivityBase {

    private CardView finalTestCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpToolbar(getString(R.string.title_activity_dashboard));
        finalTestCardView = (CardView) findViewById(R.id.final_test_dashboard);
        finalTestCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, TestChartActivity.class);

                intent.putExtra(TEST_MODE, Test.FINAL_TEST_MODE);
                startActivity(intent);
            }
        });
    }
}
